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


public class ProgressBook extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    EditText etTarget;
    ListView lvList;
    String apiAddress;

    String query;

    MyProBookAdapter adapter;
    ArrayList<MyBook> resultList;
    ArrayList<MyBook> proList;
    NaverBookXmlParser parser;
    NaverNetworkManager networkManager;
    ImageFileManager imgFileManager;

    MyBookDBManager myBookDBManager;
    MyReportDBManager myReportDBManager;

    TextView tvTitle;
    TextView tvAuthor;
    TextView tvIsbn;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_mybook);

        lvList = findViewById(R.id.pro_book);

        resultList = new ArrayList();
        adapter = new MyProBookAdapter(this, R.layout.listview_book, resultList);
        lvList.setAdapter(adapter);

        myBookDBManager = new MyBookDBManager(this);
        myReportDBManager = new MyReportDBManager(this);

        apiAddress = getResources().getString(R.string.api_url);
        parser = new NaverBookXmlParser();
        networkManager = new NaverNetworkManager(this);
        networkManager.setClientId(getResources().getString(R.string.client_id));
        networkManager.setClientSecret(getResources().getString(R.string.client_secret));
        imgFileManager = new ImageFileManager(this);

        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ProgressBook.this);
                builder.setTitle("도서 삭제")
                        .setMessage("삭제하면 독서기록도 함께 삭제되는데 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (myReportDBManager.removeReport(resultList.get(pos).get_id())) {
                                    if (myBookDBManager.removeBook(resultList.get(pos).get_id())) {
                                        Toast.makeText(ProgressBook.this, "삭제완료", Toast.LENGTH_SHORT).show();
                                        onResume();
                                    }
                                } else {
                                    Toast.makeText(ProgressBook.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, null)
                        .setCancelable(false)
                        .show();

                return true;
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                Intent intent = new Intent(ProgressBook.this, UpdateBook.class);
                intent.putExtra("update_id",String.valueOf(resultList.get(pos).get_id()));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 임시 파일 삭제
        imgFileManager.clearTemporaryFiles();

    }
    protected void onResume() {
        super.onResume();
        proList = myBookDBManager.getAllProBook();
        resultList.clear();
        resultList.addAll(proList);
        adapter.setList(resultList);
        adapter.notifyDataSetChanged();
    }






}
