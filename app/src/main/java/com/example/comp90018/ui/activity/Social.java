package com.example.comp90018.ui.activity;

import com.example.comp90018.R;

import java.util.ArrayList;

public class Social
{
    private static ArrayList<Social> userArrayList;

    private String id;
    private String name;

    public Social(String id, String name)
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

    public static void initSocial()
    {
        userArrayList = new ArrayList<>();
        Social user1 = new Social("0", "Family");
        userArrayList.add(user1);

        Social user2 = new Social("1", "Friends");
        userArrayList.add(user2);

        Social user3 = new Social("2", "Date");
        userArrayList.add(user3);

        Social user4 = new Social("3", "Party");
        userArrayList.add(user4);

    }

    public int getImage()
    {
        switch (getId())
        {
            case "0":
                return R.drawable.socialicon0;
            case "1":
                return R.drawable.socialicon1;
            case "2":
                return R.drawable.socialicon2;
            case "3":
                return R.drawable.socialicon3;

        }
        return R.drawable.socialicon0;
    }

    public static ArrayList<Social> getUserArrayList()
    {
        return userArrayList;
    }
}
