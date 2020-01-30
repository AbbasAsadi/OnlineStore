package com.example.onlinestore.model.comment;

import com.google.gson.annotations.SerializedName;

public class UpItem {

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
                "UpItem{" +
                        "href = '" + href + '\'' +
                        "}";
    }
}