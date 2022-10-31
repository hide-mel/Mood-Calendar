package com.example.comp90018;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Objects;

public class LoginPage extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    ImageButton logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page2);

        //check if the file exists
        File dir = new File(getApplicationContext().getFilesDir(), "config.txt");
        if(dir.exists()){
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }

        //gender spinner
        Spinner spinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //birthday datepicker
        initDatePicker();
        dateButton = findViewById(R.id.birthday);
        dateButton.setText(getTodaysDate());

        //img button
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login_page2);
        logIn =(ImageButton)findViewById(R.id.imageButton5);
        logIn.setOnClickListener(this);
    }

    //gender spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    //gender spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day,month,year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "Mar";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        //default
        return "JAN";
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton5:
                try {
                    //get text from text boxes
                    TextInputEditText firstName = (TextInputEditText)findViewById(R.id.firstName);
                    //Log.d("test", "abc");
                    String firstNameTxt = Objects.requireNonNull(firstName.getText()).toString();
                    //Log.d("test", "abcd");
                    TextInputEditText lastName = findViewById(R.id.lastName);
                    String lastNameTxt = Objects.requireNonNull(lastName.getText()).toString();
                    //Log.d("test1", firstNameTxt);
                    Spinner spinner = (Spinner)findViewById(R.id.gender_spinner);
                    String genderText = spinner.getSelectedItem().toString();

                    String buttonText = dateButton.getText().toString();
                    //Log.d("test1", firstNameTxt);
                    //Log.d("test1", lastNameTxt);
                    //Log.d("test1", genderText);
                    //Log.d("test1", buttonText);

                    String data = firstNameTxt + "\n" + lastNameTxt + "\n" + genderText + "\n" + buttonText;
                    //Log.d("test3", data);
                    //save the data
                    writeToFile(data);

                    //start new activity
                    startActivity(new Intent(LoginPage.this, MainActivity.class));

                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;
        }
    }


    private void writeToFile(String data) {
        try {
            File path = getApplicationContext().getFilesDir();
            FileOutputStream writer = new FileOutputStream(new File(path, "config.txt"));
            writer.write(data.getBytes());
            //Log.d("test2", "done!!!");
            writer.close();
            //Toast.makeText(getApplicationContext(),"Wrote to file:"+ path.toString() +" config.txt", Toast.LENGTH_SHORT).show();
            //Log.d("test2", "Wrote to file:"+ path.toString() +" config.txt");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}