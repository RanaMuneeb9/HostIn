package com.munib.hostin.DataModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MuhammadMusa on 10/24/2017.
 */

public class HostelsData implements Serializable {

    private int id;
    private String name,about,email,phone,mobile,type;
    private double latitude,longitude;
    ArrayList<Facilities> facilities;
    ArrayList<Reviews> reviews;
    ArrayList<RoomTypes> roomTypes;
    ArrayList<String> images;

    public HostelsData(int id, String name, double latitude, double longitude, String about, String email, String phone, String mobile, String type, ArrayList<Facilities> facilities, ArrayList<Reviews> reviews,ArrayList roomTypes,ArrayList images)
    {
       this.id=id;
       this.name=name;
       this.latitude=latitude;
       this.longitude=longitude;
       this.about=about;
       this.email=email;
       this.phone=phone;
       this.mobile=mobile;
       this.type=type;
       this.facilities=facilities;
       this.reviews=reviews;
       this.roomTypes=roomTypes;
       this.images=images;
    }

    public ArrayList<RoomTypes> getRoomTypes() {
        return roomTypes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ArrayList<Facilities> getFacilities() {
        return facilities;
    }

    public ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public String getAbout() {
        return about;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public double getAverageReview()
    {
        double total=0;
        for(int i=0;i<reviews.size();i++)
        {
            total+=reviews.get(i).getReview_overall();
        }
        return total/reviews.size();
    }
}
