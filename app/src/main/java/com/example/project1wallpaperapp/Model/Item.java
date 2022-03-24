package com.example.project1wallpaperapp.Model;

public class Item {
    private  String tags, ImageUrl;
    private int likes;
    public boolean clicked;

    public Item(String tags, String url, int likes,boolean clicked) {
        this.tags = tags;
        this.ImageUrl = url;
        this.likes = likes;
        this.clicked = clicked;

    }


    public String getTags() {
        return tags;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isClicked() {
        return clicked;
    }

    public String getImageUrl() {
        return ImageUrl;
    }


    public int getLikes() {
        return likes;
    }

}
