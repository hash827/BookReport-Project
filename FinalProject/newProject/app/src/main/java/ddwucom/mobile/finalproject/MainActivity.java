package ddwucom.mobile.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ImageView img1;
    ImageView img2;
    ImageView img3;
    String address ;
    InterparkXmlParser parser;
    NaverNetworkManager networkManager;
    ImageFileManager imgFileManager;
    ArrayList<MyBook> resultList;

    private Integer[] imageID = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_round};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView)findViewById(R.id.img_best1);
        img2 = (ImageView)findViewById(R.id.img_best2);
        img3= (ImageView)findViewById(R.id.img_best3);
        StrictMode.ThreadPolicy pol
                = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(pol);
        resultList = new ArrayList();
        address = "https://book.interpark.com/api/bestSeller.api?key=";
        address = address + getResources().getString(R.string.interparkKey);
        address = address + "&categoryId=100&maxResults=1";

        parser = new InterparkXmlParser();
        resultList = parser.parse(downloadContents(address));
        networkManager = new NaverNetworkManager(this);
        imgFileManager = new ImageFileManager(this);


        new GetImageAsyncTask(img1).execute(resultList.get(1).getImage());
        new GetImageAsyncTask(img2).execute(resultList.get(2).getImage());
        new GetImageAsyncTask(img3).execute(resultList.get(3).getImage());

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button:
                Intent intent = new Intent(this, SearchNewBook.class);
                startActivity(intent);
                break;
            case R.id.btn_update_book:
                Intent intent2 = new Intent(this, ProgressBook.class);
                startActivity(intent2);
                break;
            case R.id.btn_search:
                Intent intent3 = new Intent(this, SearchBook.class);
                startActivity(intent3);
                break;
            case R.id.btn_search_library:
                Intent intent4= new Intent(this, LibraryList.class);
                startActivity(intent4);
                break;

        }


    }
    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");
        }

        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = null;
            // networking
            result = networkManager.downloadContents(address);
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_LONG).show();
            if(result == null) return "Error";
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            //resultList = parser.parse(result);
            progressDlg.dismiss();
        }

    }
    class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        String imageAddress;
        ImageView imageView;

        public GetImageAsyncTask(ImageView img) {
            imageView = img;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageAddress = params[0];
            Bitmap result = null;
            result = networkManager.downloadImage(imageAddress);

            return result;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }

        }
    }
    public String downloadContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        if (!isOnline()) return null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }
    private String readStreamToString(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }


    /* 네트워크 환경 조사 */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {

        // 클라이언트 아이디 및 시크릿 그리고 요청 URL 선언
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);


        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }
}
