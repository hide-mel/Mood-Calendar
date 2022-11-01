package com.example.comp90018.ui.activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comp90018.R;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather>
{

    LayoutInflater layoutInflater;

    public WeatherAdapter(@NonNull Context context, int resource, @NonNull List weather
    )
    {
        super(context, resource, weather);
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View rowView = layoutInflater.inflate(R.layout.custom_spinner_adapter, null,true);
        Weather user = getItem(position);
        TextView textView = (TextView)rowView.findViewById(R.id.nameTextView);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.imageIcon);
        textView.setText(user.getName());
        imageView.setImageResource(user.getImage());
        return rowView;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.custom_spinner_adapter, parent,false);

        Weather user = getItem(position);
        TextView textView = (TextView)convertView.findViewById(R.id.nameTextView);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageIcon);
        textView.setText(user.getName());
        imageView.setImageResource(user.getImage());
        return convertView;
    }

}
