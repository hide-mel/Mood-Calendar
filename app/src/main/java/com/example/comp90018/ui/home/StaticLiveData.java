package com.example.comp90018.ui.home;

import androidx.lifecycle.MutableLiveData;

import java.util.Map;

public class StaticLiveData extends MutableLiveData<Map<String,String>> {

    private static final StaticLiveData INSTANCE= new StaticLiveData();

    private StaticLiveData(){

    }

    public static StaticLiveData getInstance(){
        return INSTANCE;
    }
}
