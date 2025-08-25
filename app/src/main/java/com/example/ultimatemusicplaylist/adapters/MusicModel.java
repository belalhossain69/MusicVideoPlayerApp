package com.example.ultimatemusicplaylist.adapters;

public class MusicModel {

    private String title;
    private int imageResId;
    private boolean isFavorite;
    private String youtubeUrl; // 🔹 Add YouTube URL here

    // Constructor
    public MusicModel(String title, int imageResId, boolean isFavorite, String youtubeUrl) {
        this.title = title;
        this.imageResId = imageResId;
        this.isFavorite = isFavorite;
        this.youtubeUrl = youtubeUrl;
    }

    // Getter and Setter for Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for Image Resource ID
    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    // Getter and Setter for Favorite
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    // 🔹 Getter and Setter for YouTube URL
    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}

