package com.munib.hostin.DataModel;

import java.io.Serializable;

public class Reviews implements Serializable
{
    int id,user_id;
    String review_description,review_date,name;
    double review_food,review_atmosphere,review_cleanliness,review_facilities,review_security,review_value,review_overall;
    public Reviews(int id,double food,double review_atmosphere,double review_cleanliness,double review_facilities,double review_value,double review_security,double review_overall,String review_description,String review_date,int user_id,String first_name,String last_name)
    {
        this.id=id;
        this.review_food=food;
        this.review_atmosphere=review_atmosphere;
        this.review_cleanliness=review_cleanliness;
        this.review_facilities=review_facilities;
        this.review_security=review_security;
        this.review_overall=review_overall;
        this.review_description=review_description;
        this.review_date=review_date;
        this.review_value=review_value;
        this.name=first_name+" "+last_name;
        this.user_id=user_id;
    }

    public String getName() {
        return name;
    }

    public double getReview_value() {
        return review_value;
    }

    public int getReviewId() {
        return id;
    }

    public double getReview_atmosphere() {
        return review_atmosphere;
    }

    public double getReview_cleanliness() {
        return review_cleanliness;
    }

    public double getReview_facilities() {
        return review_facilities;
    }

    public double getReview_food() {
        return review_food;
    }

    public double getReview_security() {
        return review_security;
    }

    public double getReview_overall() {
        return review_overall;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getReview_date() {
        return review_date;
    }

    public String getReview_description() {
        return review_description;
    }

}