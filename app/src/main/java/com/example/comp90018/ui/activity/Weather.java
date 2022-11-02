package com.example.comp90018.ui.activity;

import com.example.comp90018.R;

import java.util.ArrayList;

public class Weather
{
    private static ArrayList<Weather> userArrayList = new ArrayList<>();

    private String id;
    private String name;

    public Weather(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void initWeather()
    {
        Weather weather1 = new Weather("0", "Sunny");
        userArrayList.add(weather1);

        Weather weather2 = new Weather("1", "Clouds");
        userArrayList.add(weather2);

        Weather weather3 = new Weather("2", "Rain");
        userArrayList.add(weather3);

        Weather weather4 = new Weather("3", "Snow");
        userArrayList.add(weather4);

        Weather weather5 = new Weather("3", "Heat");
        userArrayList.add(weather5);

        Weather weather6 = new Weather("3", "Storm");
        userArrayList.add(weather6);

        Weather weather7 = new Weather("3", "Wind");
        userArrayList.add(weather7);
    }

    public int getImage()
    {
        switch (getId())
        {
            case "0":
                return R.drawable.weathericon0;
            case "1":
                return R.drawable.weathericon1;
            case "2":
                return R.drawable.weathericon2;
            case "3":
                return R.drawable.weathericon3;
            case "4":
                return R.drawable.weathericon4;
            case "5":
                return R.drawable.weathericon5;
            case "6":
                return R.drawable.weathericon6;
        }
        return R.drawable.weathericon0;
    }

    public static ArrayList<Weather> getUserArrayList()
    {
        return userArrayList;
    }
}
