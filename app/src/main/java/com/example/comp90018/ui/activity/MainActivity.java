package com.example.comp90018.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity
{

    private Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User.initUsers();
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);

        SpinnerAdapter customAdapter = new SpinnerAdapter(this, R.layout.custom_spinner_adapter, User.getUserArrayList());
        spinner1.setAdapter(customAdapter);
    }


}
