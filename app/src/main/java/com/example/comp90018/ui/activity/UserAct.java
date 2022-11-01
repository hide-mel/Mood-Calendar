package com.example.comp90018.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.comp90018.R;


public class UserAct extends AppCompatActivity
{

    private Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_activity);
        Social.initSocial();
        Hobbies.initHobbies();
        
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        SpinnerAdapter customAdapter1 = new SpinnerAdapter(this, R.layout.custom_spinner_adapter, Social.getUserArrayList());
        spinner1.setAdapter(customAdapter1);
        SpinnerAdapter customAdapter2 = new SpinnerAdapter(this, R.layout.custom_spinner_adapter, Hobbies.getUserArrayList());
        spinner2.setAdapter(customAdapter2);
    }


}
