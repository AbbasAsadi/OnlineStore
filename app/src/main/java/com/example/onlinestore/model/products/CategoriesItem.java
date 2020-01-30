package com.example.onlinestore.model.products;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return
                "CategoriesItem{" +
                        "name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",slug = '" + slug + '\'' +
                        "}";
    }
}