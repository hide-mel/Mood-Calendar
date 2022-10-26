package com.example.project2discover.ui.discover;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project2discover.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.article_page_title)
    TextView title;

    @BindView(R.id.article_page_author)
    TextView author;

    @BindView(R.id.article_page_image)
    ImageView image;

    @BindView(R.id.article_page_content)
    TextView content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_page);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        image.setImageResource(intent.getIntExtra("image",0));
        content.setText(intent.getStringExtra("content"));

    }
}