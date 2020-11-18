package ddwucom.mobile.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.os.StrictMode;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class LibraryList extends AppCompatActivity {

    EditText etTarget;
    ListView lvList;
    String apiAddress;
    private Context context;

    String query;

    LibraryAdapter adapter;
    ArrayList<LibraryDto> resultList;
    LibraryXmlParser parser;
    NaverNetworkManager networkManager;

    TextView tvTitle;
    TextView tvAuthor;
    TextView tvIsbn;
    ImageView imageView;
    String isbn;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_library);

        lvList = findViewById(R.id.lv_library);

        resultList = new ArrayList();
        adapter = new LibraryAdapter(this, R.layout.listview_library, resultList);
        lvList.setAdapter(adapter);
        StrictMode.ThreadPolicy pol
                = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(pol);
        apiAddress = getResources().getString(R.string.library_url);
        parser = new LibraryXmlParser();
        address= "http://openapi.seoul.go.kr:8088/";
        address = address + getResources().getString(R.string.librarykey);
        address = address + "/xml/SeoulPublicLibraryInfo/1/25/";
        resultList = parser.parse(downloadContents(address));

        adapter.setList(resultList);
        adapter.notifyDataSetChanged();

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Intent intent = new Intent(LibraryList.this,LibraryDetail.class);
                intent.putExtra("libName",resultList.get(pos).getLibName());
                intent.putExtra("address",resultList.get(pos).getAddress());
                intent.putExtra("tel",resultList.get(pos).getTel());
                intent.putExtra("homepage",resultList.get(pos).getHomepage());
                intent.putExtra("closed",resultList.get(pos).getClosed());
                intent.putExtra("operating",resultList.get(pos).getOperatingTime());
                intent.putExtra("xcnts",resultList.get(pos).getXcnts());
                intent.putExtra("ydnts",resultList.get(pos).getYdnts());

                startActivity(intent);

            }
        });

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

