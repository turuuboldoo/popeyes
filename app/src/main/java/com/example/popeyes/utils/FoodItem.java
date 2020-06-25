package com.example.popeyes.utils;

import org.json.JSONObject;

public class FoodItem {

    private int id;
    private String title;
    private String image;
    private String cover;
    private String description;
    private String price;

    private FoodItem(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.image = builder.image;
        this.cover = builder.cover;
        this.description = builder.description;
        this.price = builder.price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public static class Builder {
        private int id;
        private String title;
        private String image;
        private String cover;
        private String description;
        private String price;

        public Builder setFromJSONObject(JSONObject object) {
            this.id = object.optInt("id");
            this.title = object.optString("title");
            this.cover = object.optString("cover");
            this.image = object.optString("image");
            this.description = object.optString("description");
            this.price = object.optString("price");
            return this;
        }

        public FoodItem build() {
            return new FoodItem(this);
        }
    }
}
