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
        Hobbies hobbies1 = new Hobbies("0", "Movies & tv");
        userArrayList.add(hobbies1);

        Hobbies hobbies2 = new Hobbies("1", "Reading");
        userArrayList.add(hobbies2);

        Hobbies hobbies3 = new Hobbies("2", "Gaming");
        userArrayList.add(hobbies3);

        Hobbies hobbies4 = new Hobbies("3", "Sport");
        userArrayList.add(hobbies4);

        Hobbies hobbies5 = new Hobbies("3", "Relax");
        userArrayList.add(hobbies5);

    }

    public int getImage()
    {
        switch (getId())
        {
            case "0":
                return R.drawable.hobbiesicon0;
            case "1":
                return R.drawable.hobbiesicon1;
            case "2":
                return R.drawable.hobbiesicon2;
            case "3":
                return R.drawable.hobbiesicon3;
            case "4":
                return R.drawable.hobbiesicon4;

        }
        return R.drawable.hobbiesicon0;
    }

    public static ArrayList<Hobbies> getUserArrayList()
    {
        return userArrayList;
    }
}
