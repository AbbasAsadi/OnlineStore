package com.example.onlinestore.model;

import com.google.gson.annotations.SerializedName;

public class CollectionItem {

    @SerializedName("href")
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return
                "CollectionItem{" +
                        "href = '" + href + '\'' +
                        "}";
    }
}