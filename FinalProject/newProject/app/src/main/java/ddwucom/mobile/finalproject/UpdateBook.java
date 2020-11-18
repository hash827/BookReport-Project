package ddwucom.mobile.finalproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class UpdateBook extends AppCompatActivity {
    String update_id;
    MyBookDBManager myBookDBManager;
    MyBook myBook;

    TextView tvTitle;
    TextView tvAuthor;
    TextView tvIsbn;
    TextView tvPublisher;
    ImageView imageView;
    EditText etContent;
    TextView tvStart;
    TextView tvEnd;
    TextView tvState;

    RatingBar ratingBar;

    int StartYear;
    int StartMonth;
    int StartDay;
    int EndYear;
    int EndMonth;
    int EndDay;

    Spinner spinner;
    Spinner spinner_state;
    String [] item;

    String bookRating = "0";

    Calendar cal;

    private ImageFileManager imageFileManager = null;
    private NaverNetworkManager networkManager = null;

    private Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_update_book);

        Intent intent = getIntent();
        update_id = intent.getStringExtra("update_id");

        myBookDBManager = new MyBookDBManager(this);
        myBook = myBookDBManager.getBook(Long.parseLong(update_id));
//
        imageFileManager = new ImageFileManager(context);
        networkManager = new NaverNetworkManager(context);

        tvTitle = findViewById(R.id.tv_book_title);
        tvAuthor = findViewById(R.id.tv_book_author);
        tvIsbn = findViewById(R.id.tv_book_isbn);
        tvPublisher = findViewById(R.id.tv_book_publisher);
        etContent = findViewById(R.id.et_book_content);
        imageView = findViewById(R.id.img_book);
        tvState = findViewById(R.id.tv_state);
//
        tvTitle.setText(myBook.getTitle());
        tvAuthor.setText(myBook.getAuthor());
        tvIsbn.setText(myBook.getIsbn());
        tvPublisher.setText(myBook.getPublisher());

        cal = Calendar.getInstance();


        tvStart = (TextView)findViewById(R.id.dt_start);
        if(myBook.getStartDate() != null){
            tvStart.setText(myBook.getStartDate());
        }
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateBook.this, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            StartYear = year;
                            StartMonth = month + 1;
                            StartDay = dayOfMonth;
                            tvStart.setText(StartYear + "." + StartMonth + "." + StartDay);

                        }
                    },  cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                    datePickerDialog.setMessage("시작날짜 선택");
                    datePickerDialog.show();
                }

        });
        tvEnd = findViewById(R.id.dt_book_end);
        if(myBook.getEndDate() != null){
            tvEnd.setText(myBook.getEndDate());
        }
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateBook.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        EndYear = year;
                        EndMonth = month + 1;
                        EndDay = dayOfMonth;
                        tvEnd.setText(EndYear + "." + EndMonth + "." + EndDay);

                    }
                },  cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                datePickerDialog.setMessage("시작날짜 선택");
                datePickerDialog.show();
            }

        });

        item = getResources().getStringArray(R.array.book_state);
        spinner = (Spinner) findViewById(R.id.sp_book_state);
        if (myBook.getBookState()!=null){
            if (myBook.getBookState().equals("읽는중")) {
                spinner.setSelection(0);
                tvState.setText(myBook.getBookState());
            }
            else{
                spinner.setSelection(1);
                tvState.setText(myBook.getBookState());
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvState.setText(item[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        ratingBar = findViewById(R.id.book_rating);
        if (myBook.getRating() != null){
            bookRating = myBook.getRating();
            ratingBar.setRating(Float.valueOf(bookRating));
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
               bookRating = Float.toString(rating);
            }
        });


        if (myBook.getContent() != null){
            etContent.setText(myBook.getContent());
        }
//
        if (myBook.getImage() == null){
            imageView.setImageResource(R.mipmap.ic_launcher);
        }

        else{
             GetImageAsyncTask imageAsyncTask = new GetImageAsyncTask();
             imageAsyncTask.execute(myBook.getImage());
            }

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_book: {
                boolean result = myBookDBManager.updateBook(new MyBook(Long.parseLong(update_id), tvIsbn.getText().toString(), etContent.getText().toString(),
                        tvStart.getText().toString(), tvEnd.getText().toString(), bookRating, tvState.getText().toString()));
                if (result) {
                    Toast.makeText(this, "수정 완료!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
                break;
            case R.id.btn_toreport: {
                Intent intent = new Intent(UpdateBook.this, ReportList.class);
                intent.putExtra("book_id", update_id);
                intent.putExtra("isbn", tvIsbn.getText().toString());
                startActivity(intent);
            }
                break;
        }
    }
    class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        String imageAddress;
        ImageView imageView = findViewById(R.id.img_book);
        Bitmap result;

        @Override
        protected Bitmap doInBackground(String... params) {
            String result = params[0];
            //String result = networkManager.downloadContents(address);
            Bitmap bitmap = null;
            bitmap = networkManager.downloadImage(result);
            return bitmap;

        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            /*작성할 부분*/
            /*네트워크에서 다운 받은 이미지 파일을 ImageFileManager 를 사용하여 내부저장소에 저장
             * 다운받은 bitmap 을 이미지뷰에 지정*/
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                //imageFileManager.saveBitmapToTemporary(bitmap,imageAddress);

            }

        }



    }




}
