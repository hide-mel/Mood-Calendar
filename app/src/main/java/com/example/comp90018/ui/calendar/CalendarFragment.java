package com.example.comp90018.ui.calendar;


import android.os.Build;
import android.os.Bundle;


//import android.arch.lifecycle.ViewModelProvider;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comp90018.R;
import com.example.comp90018.database.RoomDB;
import com.example.comp90018.database.User;
import com.example.comp90018.databinding.FragmentCalendarBinding;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener{

    private FragmentCalendarBinding binding;

    private View view;
    private int layout = R.layout.fragment_calendar;

    private RecyclerView calendarRecyclerView;
    private TextView monthYearText;
    private LocalDate selectedDate;
    private RoomDB roomDB;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(layout,container,false);

        initWidgets();
        selectedDate = LocalDate.now();

        setMonthView();

        view.findViewById(R.id.calendar_backward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction(view);
            }
        });
        view.findViewById(R.id.calendar_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction(view);
            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initWidgets()
    {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);

    }

    private void setMonthView()
    {
        // set calendar title of month-year
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);


        String year = String.valueOf(selectedDate.getYear());
        String month = String.valueOf(selectedDate.getMonthValue());
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, this.getActivity(), year, month);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 7);

        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();

        }

        //TODO: delete following code
        User user = new User();
        user.setDay(String.valueOf(LocalDate.now().getDayOfMonth()));
        user.setMonth(String.valueOf(LocalDate.now().getMonthValue()));
        user.setYear(String.valueOf(LocalDate.now().getYear()));
        user.setEmotion("HAPPY");
        user.setActivity("");
        roomDB = RoomDB.getInstance(getActivity());
        roomDB.userDao().insert(user);
    }
}
