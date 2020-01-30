package com.example.onlinestore.model.comment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Links {

    @SerializedName("self")
    private List<SelfItem> self;

    @SerializedName("collection")
    private List<CollectionItem> collection;

    @SerializedName("up")
    private List<UpItem> up;

    public List<SelfItem> getSelf() {
        return self;
    }

    public void setSelf(List<SelfItem> self) {
        this.self = self;
    }

    public List<CollectionItem> getCollection() {
        return collection;
    }

    public void setCollection(List<CollectionItem> collection) {
        this.collection = collection;
    }

    public List<UpItem> getUp() {
        return up;
    }

    public void setUp(List<UpItem> up) {
        this.up = up;
    }

    @Override
    public String toString() {
        return
                "Links{" +
                        "self = '" + self + '\'' +
                        ",collection = '" + collection + '\'' +
                        ",up = '" + up + '\'' +
                        "}";
    }
}