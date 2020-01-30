package com.example.onlinestore.model.products;

import com.google.gson.annotations.SerializedName;

public class ImagesItem {

    @SerializedName("date_modified_gmt")
    private String dateModifiedGmt;

    @SerializedName("date_modified")
    private String dateModified;

    @SerializedName("src")
    private String src;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("name")
    private String name;

    @SerializedName("alt")
    private String alt;

    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;

    @SerializedName("id")
    private int id;

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "ImagesItem{" +
                        "date_modified_gmt = '" + dateModifiedGmt + '\'' +
                        ",date_modified = '" + dateModified + '\'' +
                        ",src = '" + src + '\'' +
                        ",date_created = '" + dateCreated + '\'' +
                        ",name = '" + name + '\'' +
                        ",alt = '" + alt + '\'' +
                        ",date_created_gmt = '" + dateCreatedGmt + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}