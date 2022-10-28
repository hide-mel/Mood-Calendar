package com.example.comp90018.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp90018.R;
import com.example.comp90018.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private View root;
    private int layout = R.layout.fragment_home;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(layout,container,false);
        return root;
    }

}