package ddwucom.mobile.finalproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class SearchBook extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    EditText etTarget;
    ListView lvList;
    String apiAddress;
    RadioGroup radioGroup;


    String query;

    MyBookAdapter adapter;
    ArrayList<NaverBookDto> resultList;
    ArrayList<NaverBookDto> list;
    NaverBookXmlParser parser;
    NaverNetworkManager networkManager;
    ImageFileManager imgFileManager;

    MyBookDBManager myBookDBManager;

    TextView tvTitle;
    TextView tvAuthor;
    TextView tvIsbn;
    ImageView imageView;
    int want_search = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_search_addbook);

        etTarget = findViewById(R.id.etTarget);
        lvList = findViewById(R.id.searchNewBookList);

        resultList = new ArrayList();
        adapter = new MyBookAdapter(this, R.layout.listview_book, resultList);
        lvList.setAdapter(adapter);

        myBookDBManager = new MyBookDBManager(this);


        parser = new NaverBookXmlParser();
        networkManager = new NaverNetworkManager(this);
        networkManager.setClientId(getResources().getString(R.string.client_id));
        networkManager.setClientSecret(getResources().getString(R.string.client_secret));
        imgFileManager = new ImageFileManager(this);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                                Intent intent = new Intent(SearchBook.this, BookDetail.class);
                                MyBook myBook = new MyBook(resultList.get(pos).getTitle(),resultList.get(pos).getAuthor(),
                                        resultList.get(pos).getIbsn(), resultList.get(pos).getPublisher(),
                                        resultList.get(pos).getImageLink(), resultList.get(pos).getPrice(),resultList.get(pos).getDescription()
                                        );
                                intent.putExtra("BookDetail",myBook);
                                startActivity(intent);
                            }

            });
        radioGroup = (RadioGroup)findViewById(R.id.search_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.searchAuthor){
                    want_search = 1;
                }
                else if(checkedId == R.id.searchTitle){
                    want_search = 2;
                }
                else if(checkedId == R.id.searchAll){
                    want_search = 0;
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 임시 파일 삭제
        imgFileManager.clearTemporaryFiles();

    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                if(want_search == 0){
                    apiAddress = getResources().getString(R.string.api_url);
                }
                else if(want_search == 1){
                    apiAddress = getResources().getString(R.string.api_url_author);
                }
                else if(want_search == 2){
                    apiAddress = getResources().getString(R.string.api_url_title);
                }
                query = etTarget.getText().toString();  // UTF-8 인코딩 필요

                try {
                    new NetworkAsyncTask().execute(apiAddress + URLEncoder.encode(query, "UTF-8"));
                }catch(UnsupportedEncodingException E){
                    E.printStackTrace();
                }
                break;
        }
    }


    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(SearchBook.this, "Wait", "Downloading...");
        }
        @Override

        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = null;

            result = networkManager.downloadContents(address);
            if(result == null) return "Error";
            Log.d(TAG,result);


            list = parser.parse(result);


            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            resultList.clear();
            resultList.addAll(list);
            adapter.setList(resultList);    // Adapter 에 결과 List 를 설정 후 notify,우리가 만든함수
            progressDlg.dismiss();
        }

    }

}

