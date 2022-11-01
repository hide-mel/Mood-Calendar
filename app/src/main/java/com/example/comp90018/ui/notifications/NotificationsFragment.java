package com.example.comp90018.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.comp90018.R;

public class NotificationsFragment extends Fragment {

    private View root;
    private int layout = R.layout.fragment_notifications;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(layout,container,false);
        return root;
    }


}