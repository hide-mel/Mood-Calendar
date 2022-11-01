package com.example.comp90018.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.comp90018.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class AddingActivities extends AppCompatActivity {

    private Button get_location;
    private String TAG = "LOCATION";
    private int Request_Code_Location = 22;
    private LocationManager locationManager;
    private Context context = this;

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
                        Log.e(TAG, "run: lat: "+location.getLatitude() );
                        Log.e(TAG, "run: lon: "+location.getLongitude() );
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        Geocoder geocoder = new Geocoder(AddingActivities.this, Locale.getDefault());
//                        if (Settings.System.getString(cr,Settings.System.AIRPLANE_MODE_ON))
//                        try {
////                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
////                            List<Address> addresses = geocoder.getFromLocation(-37.796,144.961,1);
//                            List<Address> addresses = geocoder.getFromLocation(lat,lon,1);
//
//
//                            if (addresses.size() > 0)
//                                Log.e(TAG, "run: address size "+addresses.size() );
//                        } catch (IOException e) {
//                            Log.e(TAG, "run: error");
//                            e.printStackTrace();
//                        }

//                        String url = "http://api.positionstack.com/v1/reverse?access_key=d39bd4087a1b7d31704a64cd216d6d59&query=";

                        String address = getAddress(sendRequest(lat,lon));
                        Log.e(TAG, "run: address from location: "+address );



                    }
                }
            }.start();


        } else {
            ActivityCompat.requestPermissions(AddingActivities.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code_Location);

        }
    }


    public String sendRequest(double lat, double lon){
        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try{

//            URL url = new URL("http://api.positionstack.com/v1/reverse?access_key=d39bd4087a1b7d31704a64cd216d6d59&query=-37.796,144.961");
//            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(5000);
//            con.setReadTimeout(5000);
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type","application/json");
//            con.setDoOutput(true);
            String url_string = "https://api.geoapify.com/v1/geocode/reverse?lat=";
            url_string = url_string+lat+"&lon="+lon+"&apiKey=90170e1139804ff49131facc23c3139f";
            URL url = new URL(url_string);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Accept", "application/json");

            int responseCode = http.getResponseCode();
//            System.out.println(responseCode);
            if (responseCode == 200){
                InputStream is = http.getInputStream();

                resultBuffer = new StringBuffer();
                String line;
                buffer = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                while ((line = buffer.readLine()) != null){
                    resultBuffer.append(line);
                }
                http.disconnect();
                return resultBuffer.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getAddress(String json){
        StringBuilder address = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(json);;
            JSONObject properties = jsonObject.getJSONArray("features")
                    .getJSONObject(0).getJSONObject("properties");
            address.append(properties.get("street"));
            address.append(",");
            address.append(properties.get("suburb"));
            address.append(",");
            address.append(properties.get("city"));
            return address.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    };







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


