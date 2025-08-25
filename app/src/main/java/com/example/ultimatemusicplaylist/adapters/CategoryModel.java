package com.example.ultimatemusicplaylist.adapters;

public class CategoryModel {

    private String name;
    private int imageResId;
    private String youtubeUrl; // 🔹 Add YouTube URL here

    // Constructor
    public CategoryModel(String name, int imageResId, String youtubeUrl) {
        this.name = name;
        this.imageResId = imageResId;
        this.youtubeUrl = youtubeUrl;
    }

    // Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for Image Resource ID
    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    // 🔹 Getter and Setter for YouTube URL
    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}

