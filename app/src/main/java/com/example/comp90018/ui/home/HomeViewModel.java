package com.example.comp90018.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Map<String,String>> mText;

    public MutableLiveData<Map<String,String>> getCurrentName(){
        if (mText == null){
            mText = new MutableLiveData<>();
        }
        return mText;
    }


}