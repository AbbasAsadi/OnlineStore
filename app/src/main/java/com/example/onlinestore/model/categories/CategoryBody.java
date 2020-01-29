package com.example.onlinestore.model.categories;

import com.google.gson.annotations.SerializedName;

public class CategoryBody {

    @SerializedName("parent")
    private int parent;

    @SerializedName("image")
    private Image image;

    @SerializedName("menu_order")
    private int menuOrder;

    @SerializedName("_links")
    private Links links;

    @SerializedName("display")
    private String display;

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int count;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                "CategoryBody{" +
                        "parent = '" + parent + '\'' +
                        ",image = '" + image + '\'' +
                        ",menu_order = '" + menuOrder + '\'' +
                        ",_links = '" + links + '\'' +
                        ",display = '" + display + '\'' +
                        ",name = '" + name + '\'' +
                        ",count = '" + count + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        ",slug = '" + slug + '\'' +
                        "}";
    }
}