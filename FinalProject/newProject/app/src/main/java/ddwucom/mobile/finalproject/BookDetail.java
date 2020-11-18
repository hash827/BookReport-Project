package ddwucom.mobile.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BookDetail extends AppCompatActivity {
    MyBook myBook;
    TextView tvTitle;
    TextView tvAuthor;
    TextView tvIsbn;
    ImageView imageView;
    TextView tvPublisher;
    TextView tvPrice;
    TextView tvDescription;
    MyBookDBManager myBookDBManager;
    Context context;

    private ImageFileManager imageFileManager = null;
    private NaverNetworkManager networkManager = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();
        myBook = (MyBook) intent.getSerializableExtra("BookDetail");

        tvAuthor = findViewById(R.id.tv_detail_author);
        tvDescription = findViewById(R.id.tv_detail_description);
        tvIsbn = findViewById(R.id.tv_detail_isbn);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvPublisher = findViewById(R.id.tv_detail_publisher);
        tvPrice = findViewById(R.id.tv_detail_price);

        networkManager = new NaverNetworkManager(context);

        tvPrice.setText(myBook.getPrice());
        tvPublisher.setText(myBook.getPublisher());
        tvTitle.setText(myBook.getTitle());
        tvDescription.setText(myBook.getDescription());
        tvIsbn.setText(myBook.getIsbn());
        tvAuthor.setText(myBook.getAuthor());

        BookDetail.GetImageAsyncTask imageAsyncTask = new BookDetail.GetImageAsyncTask();
        imageAsyncTask.execute(myBook.getImage());


        myBookDBManager = new MyBookDBManager(this);





    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_book_indetail: {
                boolean result = myBookDBManager.addNewBook(myBook);
                if(result){
                    Toast.makeText(this, "추가 완료!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        String imageAddress;
        ImageView imageView = findViewById(R.id.detail_img);
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




    }}
