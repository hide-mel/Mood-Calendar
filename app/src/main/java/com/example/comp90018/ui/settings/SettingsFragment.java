package com.example.comp90018.ui.settings;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.comp90018.R;

public class SettingsFragment extends Fragment {

    private View view;
    private int layout = R.layout.fragment_settings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layout,container,false);


        return view;
    }

}