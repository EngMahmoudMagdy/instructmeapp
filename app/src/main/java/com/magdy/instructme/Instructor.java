package com.magdy.instructme;

import java.io.Serializable;
import java.util.List;

/**
 * Created by engma on 7/15/2017.
 */

public class Instructor extends User implements Serializable{
    private List<Reviewer> reviews ;
    private List<String> courses;


    public List<Reviewer> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviewer> reviews) {
        this.reviews = reviews;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
