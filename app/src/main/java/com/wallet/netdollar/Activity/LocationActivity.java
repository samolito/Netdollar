package com.wallet.netdollar.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wallet.netdollar.R;
public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    TextView txtTitle;
    private GoogleMap mMap;
    double lat;//=21.034024;
    double longi;//=105.766267;
    MarkerOptions mark1, mark2;
    Boolean mLocationPermissionGranted=false;
    final  int REQUEST_LOCATION=1;
    String mLastKnownLocation = null;
    LocationManager locationManager;//=(LocationManager) getSystemService(Context.LOCATION_SERVICE);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle=findViewById(R.id.txttitle);
        txtTitle.setText(R.string.titre_map);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
             .findFragmentById(R.id.map);
       mapFragment.getMapAsync( LocationActivity.this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMyLocationEnabled(true);
        updateLocationUI();
        LatLng hanoi = new LatLng(lat, longi);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hanoi,15));
        mMap.getMaxZoomLevel();
    }
    public void getLocationPermission(){
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted=true;
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
         //   if(location!=null) {
                lat = location.getLatitude();
                longi = location.getLongitude();
          /*  }
            else {
              location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }*/
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
