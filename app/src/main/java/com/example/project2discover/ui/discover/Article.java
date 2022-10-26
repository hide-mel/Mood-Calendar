package com.example.project2discover.ui.discover;

import java.security.PrivateKey;

public class Article {

    private int ArticleImage;
    private String ArticleTitle;
    private String ArticleAuthor;
    private String ArticleContent;
    private String ArticleImage_name;

    public Article(int ArticleImage, String ArticleTitle, String ArticleAuthor,String Content,String articleImage_name){
        this.ArticleImage = ArticleImage;
        this.ArticleTitle = ArticleTitle;
        this.ArticleAuthor = ArticleAuthor;
        this.ArticleContent = Content;
        this.ArticleImage_name = articleImage_name;
    }

    public void setArticleContent(String articleContent) {
        this.ArticleContent = articleContent;
    }

    public void setArticleImage_name(String articleImage_name) {
        ArticleImage_name = articleImage_name;
    }

    public void setArticleImage(int articleImage) {
        ArticleImage = articleImage;
    }

    public int getArticleImage(){
        return ArticleImage;
    }

    public String getArticleAuthor() {
        return ArticleAuthor;
    }

    public String getArticleTitle(){
        return ArticleTitle;
    }

    public String getArticleContent() { return ArticleContent; }

    public String getArticleImage_name() { return ArticleImage_name; }


}
