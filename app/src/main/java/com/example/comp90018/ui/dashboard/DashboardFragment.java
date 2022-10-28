package com.example.comp90018.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp90018.R;
import com.example.comp90018.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

private View view;
private int layout = R.layout.fragment_dashboard;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layout,container,false);

        return view;
    }


}