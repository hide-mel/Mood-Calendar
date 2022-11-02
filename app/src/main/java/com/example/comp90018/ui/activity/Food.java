package com.example.comp90018.ui.activity;

import com.example.comp90018.R;

import java.util.ArrayList;

public class Food
{
    private static ArrayList<Food> userArrayList = new ArrayList<>();

    private String id;
    private String name;

    public Food(String id, String name)
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

    public static void initFood()
    {
        Food hobbies1 = new Food("0", "Eat healthy");
        userArrayList.add(hobbies1);

        Food hobbies2 = new Food("1", "Fast food");
        userArrayList.add(hobbies2);

        Food hobbies3 = new Food("2", "Homemade");
        userArrayList.add(hobbies3);

        Food hobbies4 = new Food("3", "Eat out");
        userArrayList.add(hobbies4);

    }

    public int getImage() {
        switch (getId()) {
            case "0":
                return R.drawable.foodicon0;
            case "1":
                return R.drawable.foodicon1;
            case "2":
                return R.drawable.foodicon2;
            case "3":
                return R.drawable.foodicon3;
        }
            return R.drawable.foodicon0;
        }

        public static ArrayList<Food> getUserArrayList()
        {
            return userArrayList;
        }

}