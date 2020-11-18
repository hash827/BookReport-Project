package ddwucom.mobile.finalproject;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ReportList extends AppCompatActivity {

    ListView lvList;

    MyReportAdapter adapter;
    ArrayList<MyReport> resultList;
    ArrayList<MyReport> list;


    MyReportDBManager myReportDBManager;

    TextView tvTitle;
    TextView tvAuthor;
    TextView tvIsbn;
    ImageView imageView;
    String isbn;
    String bookId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("book_id");
        isbn = intent.getStringExtra("isbn");
        lvList = findViewById(R.id.reportList);

        resultList = new ArrayList<MyReport>();
        list = new ArrayList<MyReport>();
        adapter = new MyReportAdapter(this, R.layout.listview_myreport, resultList);
        lvList.setAdapter(adapter);

        myReportDBManager = new MyReportDBManager(this);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                Intent intent = new Intent(ReportList.this, UpdateReport.class);
                intent.putExtra("update_report",String.valueOf(resultList.get(pos).get_id()));
                startActivity(intent);
            }
        });
        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ReportList.this);
                builder.setTitle("독서기록 삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (myReportDBManager.removeReport(resultList.get(pos).get_id())) {
                                    Toast.makeText(ReportList.this, "삭제완료", Toast.LENGTH_SHORT).show();
                                    onResume();
                                } else {
                                    Toast.makeText(ReportList.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, null)
                        .setCancelable(false)
                        .show();

                return true;
            }
        });

    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_to_add_report:
                Intent intent = new Intent(ReportList.this, AddReport.class);
                intent.putExtra("book_id",bookId);
                intent.putExtra("isbn",isbn);
                startActivity(intent);

        }
    }
    protected void onResume() {
        super.onResume();
        list = myReportDBManager.getAllReport(bookId);
        resultList.clear();
        resultList.addAll(list);
        adapter.setList(resultList);
        adapter.notifyDataSetChanged();
    }




}
