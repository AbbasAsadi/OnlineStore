package com.example.onlinestore.model.products;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "product")
public class ShoppingBasketProduct {
    @Id(autoincrement = true)
    private Long id;
    private int productId;
    private int number;
    private String title;
    private String shortDescription;
    private String imageSrc;
    private String price;
    private String finalPrice;

    public ShoppingBasketProduct(int productId, int number, String title,
                                 String shortDescription, String imageSrc, String price,
                                 String finalPrice) {
        this.productId = productId;
        this.number = number;
        this.title = title;
        this.shortDescription = shortDescription;
        this.imageSrc = imageSrc;
        this.price = price;
        this.finalPrice = finalPrice;
    }

    @Generated(hash = 1888738042)
    public ShoppingBasketProduct(Long id, int productId, int number, String title,
            String shortDescription, String imageSrc, String price, String finalPrice) {
        this.id = id;
        this.productId = productId;
        this.number = number;
        this.title = title;
        this.shortDescription = shortDescription;
        this.imageSrc = imageSrc;
        this.price = price;
        this.finalPrice = finalPrice;
    }

    @Generated(hash = 507832333)
    public ShoppingBasketProduct() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageSrc() {
        return this.imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFinalPrice() {
        return this.finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }
    

}
