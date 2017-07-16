package com.magdy.instructme;

import java.io.Serializable;
import java.util.List;

/**
 * Created by engma on 7/12/2017.
 */

public class Course implements Serializable {
    private String id , name  , category , description , currency , image ;
    private int  price , oldPrice;
    private List<Reviewer> reviews ;
    private boolean isSaved ;

    public void setReviews(List<Reviewer> reviews) {
        this.reviews = reviews;
    }

    public List<Reviewer> getReviews() {
        return reviews;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
