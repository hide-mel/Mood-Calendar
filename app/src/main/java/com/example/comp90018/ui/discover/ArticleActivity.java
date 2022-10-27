package com.example.comp90018.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.appcompat.app.AppCompatActivity;


import com.example.comp90018.R;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

//    @BindView(R.id.article_page_title)
    private TextView title;

//    @BindView(R.id.article_page_author)
    private TextView author;

//    @BindView(R.id.article_page_image)
    private ImageView image;

//    @BindView(R.id.article_page_content)
    private TextView content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_page);

//        ButterKnife.bind(this);
        title = findViewById(R.id.article_page_title);
        author = findViewById(R.id.article_page_author);
        image = findViewById(R.id.article_page_image);
        content = findViewById(R.id.article_page_content);


        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        image.setImageResource(intent.getIntExtra("image",0));
        content.setText(intent.getStringExtra("content"));

    }
}