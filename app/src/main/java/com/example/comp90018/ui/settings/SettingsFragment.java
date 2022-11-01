package com.example.comp90018.ui.settings;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

//
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatDelegate;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.comp90018.R;

import java.util.Calendar;



public class SettingsFragment extends Fragment {

//    public static int SETTING_FRAGMENT = R.layout.fragment_settings;
    private int layout = R.layout.fragment_settings;
//    static  String LAYOUT_TYPE = "type";
    private View view;


    private EditText setting_first_name;
    private EditText setting_last_name;
    private Spinner gender;
    private TextView setting_DOB;
    private Switch night_switch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(layout,container,false);

        initialize(view);
        return view;
    }

    private void initialize(View view) {

        setting_first_name = view.findViewById(R.id.setting_first_name);
        setting_last_name = view.findViewById(R.id.setting_last_name);
        gender = view.findViewById(R.id.spinner_gender);
        setting_DOB = view.findViewById(R.id.setting_DOB);
        night_switch = view.findViewById(R.id.night_switch);

        SharedPreferences sp = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String first_name = sp.getString("first_name",null);
        String last_name = sp.getString("last_name",null);
        setting_first_name.setText(first_name);
        setting_last_name.setText(last_name);



        String[] gender_items = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item,gender_items);
        gender.setAdapter(genderAdapter);
        setSpinnerItemSelectedByValue(gender,sp.getString("gender","Prefer not to respond"));

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_gender = adapterView.getItemAtPosition(i).toString();
                editor.putString("gender",selected_gender);
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Calendar calendar = Calendar.getInstance();

        setting_DOB.setText(sp.getString("DOB",null));
        setting_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),
                        R.style.MySpinnerDatePickerStyle, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String input_DOB = day+"/"+(++month)+"/"+year;
                        setting_DOB.setText(input_DOB);
                        editor.putString("DOB",input_DOB);
                        editor.commit();
                    }
                },calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH)+1,
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        int currentMode = AppCompatDelegate.getDefaultNightMode();
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            night_switch.setChecked(true);
        } else {
            night_switch.setChecked(false);
        }

        night_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    Log.e("switch", "onCheckedChanged: open night modeeeeeeeeeeeeeeeeee" );
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                    editor.putBoolean("night_mode",true);
                    editor.commit();
                    restartCurrentActivity();
                } else {
                    Log.e("switch", "onCheckedChanged: offfffffffffffffffffffffff" );
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("night_mode",false);
                    editor.commit();
                    restartCurrentActivity();
                }
            }
        });
    }

    private void restartCurrentActivity() {
        save_sp();
        getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        getActivity().recreate();
    }


    private void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        int item_counts = adapter.getCount();
        for (int i=0; i<item_counts; i++) {
            if (value.equals(adapter.getItem(i).toString())) {
                spinner.setSelection(i,true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        save_sp();
    }

    public void save_sp() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("user_info",
                Context.MODE_PRIVATE).edit();

        String input_first_name = setting_first_name.getText().toString();
        String input_last_name = setting_last_name.getText().toString();
        editor.putString("first_name",input_first_name);
        editor.putString("last_name",input_last_name);
        editor.commit();

    }

}