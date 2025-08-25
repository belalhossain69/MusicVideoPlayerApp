package com.example.ultimatemusicplaylist.adapters;

public class SearchItem {
    public enum Type {
        MUSIC,
        CATEGORY
    }

    private String name;
    private int imageResId;
    private Type type;
    private String youtubeUrl; // 🔹 ADD THIS FIELD

    public SearchItem(String name, int imageResId, Type type, String youtubeUrl) {
        this.name = name;
        this.imageResId = imageResId;
        this.type = type;
        this.youtubeUrl = youtubeUrl; // 🔹 SET IT IN CONSTRUCTOR
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public Type getType() { return type; }
    public String getYoutubeUrl() { return youtubeUrl; } // 🔹 ADD GETTER

    @Override
    public String toString() {
        return name; // for AutoCompleteTextView display
    }
}

