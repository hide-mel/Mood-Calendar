package com.example.comp90018.ui.activity;

import com.example.comp90018.R;

import java.util.ArrayList;

public class Mood
{
    private static ArrayList<Mood> userArrayList = new ArrayList<>();

    private String id;
    private String name;

    public Mood(String id, String name)
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

    public static void initmood()
    {
        userArrayList = new ArrayList<>();
        Mood mood1 = new Mood("0", "HAPPY");
        userArrayList.add(mood1);

        Mood mood2 = new Mood("1", "SAD");
        userArrayList.add(mood2);

        Mood mood3 = new Mood("2", "ANGRY");
        userArrayList.add(mood3);

        Mood mood4 = new Mood("3", "CONFUSED");
        userArrayList.add(mood4);

        Mood mood5 = new Mood("4", "DISGUSTED");
        userArrayList.add(mood5);

        Mood mood6 = new Mood("5", "SURPRISED");
        userArrayList.add(mood6);

        Mood mood7 = new Mood("6", "CALM");
        userArrayList.add(mood7);

        Mood mood8 = new Mood("7", "UNKNOWN");
        userArrayList.add(mood8);

        Mood mood9 = new Mood("8", "FEAR");
        userArrayList.add(mood9);

    }

    public int getImage()
    {
        switch (getId())
        {
            case "0":
                return R.drawable.happy_emoji;
            case "1":
                return R.drawable.sad_emoji;
            case "2":
                return R.drawable.angry_emoji;
            case "3":
                return R.drawable.confused_emoji;
            case "4":
                return R.drawable.disgusted_emoji;
            case "5":
                return R.drawable.surprise_emoji;
            case "6":
                return R.drawable.calm_emoji;
            case "7":
                return R.drawable.unknown_emoji;
            case "8":
                return R.drawable.fear_emoji;
        }
        return R.drawable.smile_face;
    }

    public static ArrayList<Mood> getUserArrayList()
    {
        return userArrayList;
    }
}
