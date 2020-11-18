package ddwucom.mobile.finalproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddReport extends AppCompatActivity {

    String book_id;
    EditText page;
    TextView date;
    EditText content;
    MyReportDBManager myReportDBManager;
    String isbn;
    Calendar cal;
    int year;
    int month;
    int day;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivity_add_report);

        Intent intent = getIntent();
        book_id = intent.getStringExtra("book_id");
        isbn = intent.getStringExtra("isbn");

        page = findViewById(R.id.et_report_page);
        date = findViewById(R.id.tv_add_report_date);
        content = findViewById(R.id.et_report_content);

        myReportDBManager = new MyReportDBManager(this);

        cal = Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReport.this, new DatePickerDialog.OnDateSetListener() {
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
        switch(v.getId()) {
            case R.id.btn_add_report:
            {
                boolean result = myReportDBManager.addNewReport(new MyReport(Long.parseLong(book_id),isbn,date.getText().toString(),
                        page.getText().toString(),content.getText().toString()));
                if (result){
                    Toast.makeText(this, "추가 완료!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

        }
    }
}
