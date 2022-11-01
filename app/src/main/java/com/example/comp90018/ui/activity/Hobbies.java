package com.example.comp90018.ui.activity;

import com.example.comp90018.R;

import java.util.ArrayList;

public class Hobbies
{
    private static ArrayList<Hobbies> userArrayList = new ArrayList<>();

    private String id;
    private String name;

    public Hobbies(String id, String name)
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

    public static void initHobbies()
    {
        userArrayList = new ArrayList<>();
        Hobbies hobbies1 = new Hobbies("0", "Family");
        userArrayList.add(hobbies1);

        Hobbies hobbies2 = new Hobbies("1", "Friends");
        userArrayList.add(hobbies2);

        Hobbies hobbies3 = new Hobbies("2", "Date");
        userArrayList.add(hobbies3);

        Hobbies hobbies4 = new Hobbies("3", "Party");
        userArrayList.add(hobbies4);

    }

    public int getImage()
    {
        switch (getId())
        {
            case "0":
                return R.drawable.usericon0;
            case "1":
                return R.drawable.usericon1;
//            case "2":
//                return R.drawable.usericon2;
//            case "3":
//                return R.drawable.usericon3;
//            case "4":
//                return R.drawable.usericon4;
//            case "5":
//                return R.drawable.usericon5;
        }
        return R.drawable.usericon0;
    }

    public static ArrayList<Hobbies> getUserArrayList()
    {
        return userArrayList;
    }
}
