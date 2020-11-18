package ddwucom.mobile.finalproject;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class LibraryDetail extends AppCompatActivity {

    TextView tvLibName;
    TextView tvTel;
    TextView tvAddress;
    TextView tvHomepage;
    TextView tvOperating;
    TextView tvClosed;
    String name;
    String tel;
    String address;
    String closed;
    String homepage;
    String operating;
    String xcnts;
    String ydnts;
    private GoogleMap mgoogleMap;
    private LocationManager locationManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        Intent intent = getIntent();
        name = intent.getStringExtra("libName");
        address = intent.getStringExtra("address");
        tel = intent.getStringExtra("tel");
        homepage = intent.getStringExtra("homepage");
        operating = intent.getStringExtra("operating");
        closed = intent.getStringExtra("closed");
        xcnts = intent.getStringExtra("xcnts");
        ydnts = intent.getStringExtra("ydnts");

        tvAddress = findViewById(R.id.tv_libAddress);
        tvLibName = findViewById(R.id.tv_libName);
        tvTel = findViewById(R.id.tv_libTel);
        tvHomepage = findViewById(R.id.tv_libHomepage);
        tvOperating = findViewById(R.id.tv_libOperating);
        tvClosed = findViewById(R.id.tv_libClosed);

        tvClosed.setText(closed);
        tvOperating.setText(operating);
        tvHomepage.setText(homepage);
        tvTel.setText(tel);
        tvAddress.setText(address);
        tvLibName.setText(name);
        tvClosed.setText(closed);
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallback);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mgoogleMap = googleMap;

            LatLng currentLoc = new LatLng(Double.parseDouble(xcnts),Double.parseDouble(ydnts) );
            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,16));

        }
    };


}
