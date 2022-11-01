package com.example.comp90018.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.comp90018.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View root;
    private int layout = R.layout.fragment_home;
    private String mode;
    private Button selfie;
    private Button photo;
    private Button manual;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(layout,container,false);
        mode = "selfie";
        selfie = root.findViewById(R.id.selfie_button);
        selfie.setOnClickListener(this);

        photo = root.findViewById(R.id.photo_button);
        photo.setOnClickListener(this);
        manual = root.findViewById(R.id.manual_button);
        manual.setOnClickListener(this);
        Button botton = root.findViewById((R.id.button));
        botton.setOnClickListener(this);
        return root;


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.selfie_button:
                selfie.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_change_button_emotion) );
                photo.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                manual.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                mode = "selfie";
                break;
            case R.id.photo_button:
                photo.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_change_button_emotion) );
                selfie.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                manual.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                mode = "photo";
                break;
            case R.id.manual_button:
                manual.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_change_button_emotion) );
                photo.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                selfie.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                mode = "manual";
                break;
            case R.id.button:
                Intent intent = new Intent(view.getContext(), AddingActivities.class);
                view.getContext().startActivity(intent);

        }
    }
}