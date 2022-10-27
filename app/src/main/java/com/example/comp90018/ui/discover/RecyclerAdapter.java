package com.example.comp90018.ui.discover;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
import android.support.v7.widget.RecyclerView;

import com.example.comp90018.R;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Article> articleList;
    private int resourceId;

    public RecyclerAdapter(List<Article> articleList, int resourceId) {
        this.articleList = articleList;
        this.resourceId = resourceId;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resourceId, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.image.setImageResource(articleList.get(i).getArticleImage());
        viewHolder.title.setText(articleList.get(i).getArticleTitle());
        viewHolder.author.setText(articleList.get(i).getArticleAuthor());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), com.example.comp90018.ui.discover.ArticleActivity.class);
                intent.setAction("ArticleActivity");
                int position = viewHolder.getAdapterPosition();
                intent.putExtra("content", articleList.get(position).getArticleContent());
                intent.putExtra("image", articleList.get(position).getArticleImage());
                intent.putExtra("author", articleList.get(position).getArticleAuthor());
                intent.putExtra("title", articleList.get(position).getArticleTitle());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //        @BindView(R.id.article_image)
        private ImageView image;
        //
//
//        @BindView(R.id.article_title)
        private TextView title;
        //
//        @BindView(R.id.article_author)
        private TextView author;

        ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.article_image);
            title = view.findViewById(R.id.article_title);
            author = view.findViewById(R.id.article_author);

//                    ButterKnife.bind(this,view);

        }

        ;

    }
}
