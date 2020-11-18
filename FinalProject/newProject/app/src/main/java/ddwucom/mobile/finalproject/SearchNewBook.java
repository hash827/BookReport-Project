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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class SearchNewBook extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    EditText etTarget;
    ListView lvList;
    String apiAddress;

    String query;

    MyBookAdapter adapter;
    ArrayList<NaverBookDto> resultList;
    NaverBookXmlParser parser;
    NaverNetworkManager networkManager;
    ImageFileManager imgFileManager;

    MyBookDBManager myBookDBManager;

    TextView tvTitle;
    TextView tvAuthor;
    TextView tvIsbn;
    ImageView imageView;


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

        apiAddress = getResources().getString(R.string.api_url);
        parser = new NaverBookXmlParser();
        networkManager = new NaverNetworkManager(this);
        networkManager.setClientId(getResources().getString(R.string.client_id));
        networkManager.setClientSecret(getResources().getString(R.string.client_secret));
        imgFileManager = new ImageFileManager(this);
        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                /* 작성할 부분 */
                /*롱클릭한 항목의 이미지 주소를 가져와 내부 메모리에 지정한 이미지 파일을 외부저장소로 이동
                 * ImageFileManager 의 이동 기능 사용
                 * 이동을 성공할 경우 파일 명, 실패했을 경우 null 을 반환하므로 해당 값에 따라 Toast 출력*/
                int pos = position;


                return true;
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchNewBook.this);
                builder.setTitle(R.string.dialog_addNewBook_title)
                        .setMessage("책을 추가하시겠습니까?")
                        .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int i = resultList.get(pos).get_id();
                                boolean result = myBookDBManager.addNewBook(new MyBook(
                                        resultList.get(pos).getTitle(), resultList.get(pos).getAuthor(),
                                        resultList.get(pos).getIbsn(), resultList.get(pos).getPublisher(),
                                        resultList.get(pos).getImageLink()));

                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, null)
                        .setCancelable(false)
                        .show();


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
                query = etTarget.getText().toString();  // UTF-8 인코딩 필요
                // OpenAPI 주소와 query 조합 후 서버에서 데이터를 가져옴
                // 가져온 데이터는 파싱 수행 후 어댑터에 설정
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
            progressDlg = ProgressDialog.show(SearchNewBook.this, "Wait", "Downloading...");
        }
        @Override
        //network작업을 networkMagager에게 부탁하는것
        //쓰레드는 실행흐름을 별개로 만드는거지 실제 작업을 하는것이 아니고 일을 맡기는것
        //arraylist를 onPostExecute으로 넘기고 싶으면
        // ArrayList<NaverBookDto> doInBackground로 형태 바꿔야함
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = null;
            // networking
            result = networkManager.downloadContents(address);
            //네트워크가 연결이 안되면 null이 반환될수도 있어서 null체크해준다
            if(result == null) return "Error";
            Log.d(TAG,result);

            // parsing
            resultList = parser.parse(result);

           /* 데이터를 한번에 미리 다 받아오는 방법
            for (NaverBookDto dto :resultList){
                Bitmap bitmap = networkManager.downloadImage(dto.getImageLink());
                if(bitmap != null){
                    imgFileManager.saveBitmapToTemporary(bitmap,dto.getImageLink());
                }
            }

            */

            return result;
        }
        @Override
        protected void onPostExecute(String result) {

            adapter.setList(resultList);    // Adapter 에 결과 List 를 설정 후 notify,우리가 만든함수
            progressDlg.dismiss();
        }

    }

}
