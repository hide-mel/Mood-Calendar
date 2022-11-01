package com.example.comp90018;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.comp90018.ui.home.HomeFragment.CAMARA_REQUEST;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.AppCompatDelegate;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comp90018.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Log.e("TAG", "onCreate: aaaaaa"+ sp.getBoolean("did_login",false));

        if (!sp.getBoolean("did_login",false)){
            startActivity(new Intent(MainActivity.this, LoginPage.class));
//            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("did_login",true);
            editor.commit();
        }

        if (sp.getBoolean("night_mode",false)) {
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(

                R.id.navigation_home, R.id.navigation_notifications,
                R.id.navigation_discover, R.id.navigation_settings)

                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this,navController);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}