package com.example.comp90018;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddingActivities extends AppCompatActivity {

    private Button get_location;
    private String TAG = "LOCATION";
    private int Request_Code_Location = 22;
    private LocationManager locationManager;
//    final ContentResolver cr = getContentResolver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_activities);

        get_location = findViewById(R.id.get_location);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationUpdates();
            }
        });
    }

    public void locationUpdates() {

        if (ActivityCompat.checkSelfPermission(AddingActivities.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "locationUpdates: get permission" );
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    1,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {

                        }
                    }
            );
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            new Thread(){
                @Override
                public void run(){
                    if (location != null) {
                        Log.e(TAG, "run: new thread" );
                        Geocoder geocoder = new Geocoder(AddingActivities.this, Locale.getDefault());
//                        if (Settings.System.getString(cr,Settings.System.AIRPLANE_MODE_ON))
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            if (addresses.size() > 0)
                                Log.e(TAG, "run: address size "+addresses.size() );
                        } catch (IOException e) {
                            Log.e(TAG, "run: error");
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
            Log.e(TAG, "locationUpdates: main thread");


        } else {
//            37.421998333333335
//            -122.084
            ActivityCompat.requestPermissions(AddingActivities.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code_Location);

        }
    }

    private void setAirPlaneMode(boolean enable) {
        int mode = enable ? 1 : 0;
        String cmd = "settings put global airplane_mode_on " + mode;
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Address getLocationAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
            if (addresses.size() > 0)
                return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

//        if (location!=null) {
//            Double lat = location.getLatitude();
//            Double lon = location.getLongitude();
//            Log.e("Location", "locationUpdates: getLatitude "+location.getLatitude());
//            Log.e("Location", "locationUpdates: getLongitude "+location.getLongitude());
////            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
//            try {
//                List<Address> addresses = geocoder.getFromLocation(lat,lon,1);
//                Log.e(TAG, "locationUpdates: "+addresses.size() );
////                    for (Address address : addresses){
////                        Log.e(TAG, "locationUpdatesssss: "+address );
////                    }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.e(TAG, "locationUpdates: lllocation" );
//
//
//        }else {
//            Log.e("Locaiton", "locationUpdates: NO location" );
//        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Request_Code_Location){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                locationUpdates();
            }
        }
    }
}
