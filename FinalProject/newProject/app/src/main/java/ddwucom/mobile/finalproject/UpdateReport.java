package ddwucom.mobile.finalproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateReport extends AppCompatActivity {
    String update_id;
    EditText content;
    EditText page;
    TextView date;
    MyReportDBHelper reportDBHelper;
    MyReportDBManager reportDBManager;
    MyReport myReport;
    Calendar cal;
    int year;
    int month;
    int day;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_report);

        Intent intent = getIntent();
        update_id = intent.getStringExtra("update_report");

        reportDBManager = new MyReportDBManager(this);
        myReport = reportDBManager.getReport(Long.parseLong(update_id));

        content = findViewById(R.id.et_update_report_content);
        page = findViewById(R.id.et_update_report_page);
        date = findViewById(R.id.tv_update_report_date);

        content.setText(myReport.getReportContent());
        page.setText(myReport.getPage());
        date.setText(myReport.getDate());
        cal = Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateReport.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        year = year;
                        month = month + 1;
                        day = dayOfMonth;
                        date.setText(year + "." + month + "." + day);

                    }
                },  cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                datePickerDialog.setMessage("날짜 선택");
                datePickerDialog.show();
            }

        });


    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_report: {
                boolean result = reportDBManager.updateReport(new MyReport(Long.parseLong(update_id), date.getText().toString(),
                        page.getText().toString(), content.getText().toString()));
                if (result) {
                    Toast.makeText(this, "수정 완료!", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            }
        }
    }



}
