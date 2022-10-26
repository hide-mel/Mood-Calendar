package com.example.project2discover.ui.discover;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2discover.R;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

import butterknife.ButterKnife;

public class DiscoverFragment extends Fragment {
    public static int RECYCLER_VIEW = R.layout.fragment_article_recycler;
    static  String LAYOUT_TYPE = "type";

    private int layout = R.layout.fragment_article_recycler;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        if (this.getArguments() != null)
            this.layout = getArguments().getInt(LAYOUT_TYPE);
        View view = inflater.inflate(layout,container,false);
        ButterKnife.bind(this,view);
        initializeList(view);

        return view;
    }


    private void initializeList(View view) {

        Gson gson = new Gson();
        String json_string = readRawResource(R.raw.articles);

        Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
        ArrayList<Article> articles = gson.fromJson(json_string,listType);

        for(Article article : articles){
            article.setArticleImage(getResourceId(article.getArticleImage_name()));
        }

        RecyclerAdapter adapter = new RecyclerAdapter(articles,R.layout.fragment_article_item);
        recyclerView = view.findViewById(R.id.discover_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        recyclerView.setAdapter(adapter);
    }


    public String readRawResource(@RawRes int res) {
        return readStream(getResources().openRawResource(res));
    }

    private String readStream(InputStream is) {
        // no delimiter, read the entire input stream
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public int getResourceId(String imgName) {
        Context context = getContext();
        int resId = getResources().getIdentifier(imgName,"drawable",context.getPackageName());
        return resId;
    }

    public static Fragment newInstance(int layout){
        Fragment fragment = new DiscoverFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE,layout);
        fragment.setArguments(bundle);

        return fragment;
    }
}
