package com.magdy.instructme;

import java.io.Serializable;

/**
 * Created by engma on 7/13/2017.
 */

public class Reviewer extends User implements Serializable {
    private String review ;
    private int rate ;
    
    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRate() {
        return rate;
    }

    public String getReview() {
        return review;
    }
}
