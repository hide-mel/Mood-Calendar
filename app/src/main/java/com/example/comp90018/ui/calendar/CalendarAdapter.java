package com.example.comp90018.ui.calendar;


//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comp90018.MainActivity;

import com.example.comp90018.R;
import com.example.comp90018.database.RoomDB;
import com.example.comp90018.database.User;

import java.util.ArrayList;
import java.util.List;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private String year;
    private String month;
    private List<User> queryRes;
    private RoomDB database;
    private Activity activity;


    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, Activity activity, String year, String month)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.activity = activity;
        this.month = month;
        this.year = year;
        database = RoomDB.getInstance(activity);
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    /**
     * bind final expression of calendar cell
     * @param holder
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        String day = daysOfMonth.get(position);
        if (!day.equals("")){
            queryRes = database.userDao().get(day,month,year);
            System.out.println(day + " " + month + " " + year);


            Log.e("calendar", "onBindViewHolder: res size" + queryRes.size());

            if (queryRes != null && queryRes.size() > 0){
                // if user has emotion on that day
                // place a icon on instead of day
                User res = queryRes.get(queryRes.size()-1);
                String emo = res.getEmotion();
                holder.dayOfMonth.setForeground(activity.getDrawable(CalendarFragment.moodToIcon(emo)));
                holder.dayOfMonth.setText(day);
            }else{
                // no emotion
                // day of date
                holder.dayOfMonth.setText(day);
            }
        }else{
            holder.dayOfMonth.setText(day);
        }
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}