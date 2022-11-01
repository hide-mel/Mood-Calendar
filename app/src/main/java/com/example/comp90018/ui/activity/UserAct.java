package com.example.comp90018.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp90018.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;


public class UserAct extends AppCompatActivity
{

    private String  selected_social, selected_hobbies, selected_food,
            selected_weather, location_info, selected_mood;
    private int Request_Code_Location = 22;
    private LocationManager locationManager;
    private Context context = this;
    private Handler handler;
    private TextView location_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_activity);
        Log.e("TAG", "onCreate: aaaaaaaaaaaaaaaaa" );
        location_view = findViewById(R.id.show_locaiton);
        location_info = null;

        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);

//        Log.e("TAG", "onCreate: "+sp.getString("location",null));
//        location_view.setText("AAA");

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        location_info = msg.getData().getString("location");
                        location_view.setText(location_info);
                        break;
                }
            }
        };

        initial_mood();

        //TODO complete name and image for food,hobbies,weather
        initial_spinners();

        initial_location();
        initial_cancel();
        initial_confirm();

    }

    private void initial_confirm() {
        Button confirm = findViewById(R.id.add_activity_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("location",location_info);
                editor.commit();

                StringBuilder activities = new StringBuilder();
                if (selected_social!=null){
                    activities.append("social: "+selected_social+"\n");
                }
                if (selected_hobbies!=null){
                    activities.append("hobby: "+selected_hobbies+"\n");
                }
                if (selected_food!=null){
                    activities.append("food: "+selected_food+"\n");
                }
                if (selected_weather!=null){
                    activities.append("weather: "+selected_weather+"\n");
                }
                if (location_info!=null){
                    activities.append("location: "+location_info+"\n");
                }
                // selected_mood
                activities.append("mood: "+selected_mood+"\n");

                EditText feeling = findViewById(R.id.feeling_text);
                String feels = feeling.getText().toString();
                if (!feels.equals("")){
                    activities.append("\n "+feels+"\n");
                }

                Log.e("TAG", "initial_confirm: "+activities.toString() );

//                Toast.makeText(context,activities,Toast.LENGTH_LONG).show();

                //TODO adding data to databse
                //activities -> activities.toString()
                //emotion -> selected_mood


            }
        });
    }

    private void initial_cancel() {
        Button cancel = findViewById(R.id.add_activity_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAct.this.finish();
            }
        });
    }

    private void initial_mood() {
        Intent intent = getIntent();
        if (intent.getStringExtra("emotion")!="manual_input"){
            LinearLayout mood = findViewById(R.id.mood_selector);
            mood.setVisibility(View.GONE);
            selected_mood = intent.getStringExtra("emotion");
        } else {
            Mood.initmood();
            Spinner spinner_mood = findViewById(R.id.mood_spinner);
            spinner_mood.setAdapter(new MoodAdapter(this, R.layout.custom_spinner_adapter, Mood.getUserArrayList()));
            spinner_mood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Mood mood = (Mood) adapterView.getItemAtPosition(i);
                    selected_mood = mood.getName();
                    Log.e("TAG", "onItemSelected: bbbb"+selected_mood );
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    selected_mood = null;
                }
            });
        }
    }

    private void initial_spinners() {
        Social.initSocial();
        Hobbies.initHobbies();
        Food.initfood();
        Weather.initweather();

        Spinner spinner_social =  findViewById(R.id.spinner1);
        Spinner spinner_hobbies = findViewById(R.id.spinner2);
        Spinner spinner_food =  findViewById(R.id.spinner3);
        Spinner spinner_weather =  findViewById(R.id.spinner4);

//        SpinnerAdapter customAdapter1 = new SpinnerAdapter(this, R.layout.custom_spinner_adapter, Social.getUserArrayList());
        spinner_social.setAdapter(new SpinnerAdapter(this, R.layout.custom_spinner_adapter, Social.getUserArrayList()));
//
        spinner_hobbies.setAdapter(new HobbiesAdapter(this, R.layout.custom_spinner_adapter, Hobbies.getUserArrayList()));

        spinner_food.setAdapter(new FoodAdapter(this, R.layout.custom_spinner_adapter, Food.getUserArrayList()));

        spinner_weather.setAdapter(new WeatherAdapter(this, R.layout.custom_spinner_adapter, Weather.getUserArrayList()));

        spinner_social.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Social social = (Social) adapterView.getItemAtPosition(i);
                selected_social = social.getName();
                Log.e("TAG", "onItemSelected: bbbb"+selected_social );
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected_social = null;
            }
        });
//        Log.e("TAG", "onCreate: 5" );
//
        spinner_hobbies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Hobbies hobbies = (Hobbies) adapterView.getItemAtPosition(i);
                selected_hobbies = hobbies.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected_hobbies = null;
            }
        });

        spinner_food.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = (Food) adapterView.getItemAtPosition(i);
                selected_food = food.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected_food = null;
            }
        });

        spinner_weather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Weather weather = (Weather) adapterView.getItemAtPosition(i);
                selected_food = weather.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected_weather = null;
            }
        });
    }


    private void initial_location() {
        Button get_location = findViewById(R.id.get_location);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_view.setHint("loading...if waiting too long, please try again");
                locationUpdates();
            }
        });

        Button clean_locaiton = findViewById(R.id.clean_location);
        clean_locaiton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                location_view.setText(null);
                location_view.setHint("get your location~");
                SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("locaiton",null);
                editor.commit();
            }
        });
    }

    private void locationUpdates() {
        if (ActivityCompat.checkSelfPermission(UserAct.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e("TAG", "locationUpdates: get premission");
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

                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        Log.e("TAG", "locationUpdates: get lat"+lat);

                        String address = getAddress(sendRequest(lat,lon));
                        Log.e("TAG", "run: address from location: "+address );
                        Message message = new Message();
                        message.what=1;
                        Bundle bundle = new Bundle();
                        bundle.putString("location",address);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }
                }
            }.start();
        } else {
            Log.e("TAG", "locationUpdates: no premission");
            ActivityCompat.requestPermissions(UserAct.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code_Location);
        }
    }

    public String sendRequest(double lat, double lon){
        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try{

            String url_string = "https://api.geoapify.com/v1/geocode/reverse?lat=";
            url_string = url_string+lat+"&lon="+lon+"&apiKey=90170e1139804ff49131facc23c3139f";
            URL url = new URL(url_string);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Accept", "application/json");

            int responseCode = http.getResponseCode();

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

    @Nullable
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
