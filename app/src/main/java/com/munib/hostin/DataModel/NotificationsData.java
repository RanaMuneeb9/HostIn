package com.munib.hostin.DataModel;

/**
 * Created by rana_ on 10/29/2017.
 */

public class NotificationsData {
    int id,hostel_id;
    String title,description,date;
    public NotificationsData(int id,int hostel_id,String title,String description,String date)
    {
        this.id=id;
        this.hostel_id=hostel_id;
        this.title=title;
        this.description=description;
        this.date=date;
    }

    public int getId() {
        return id;
    }

    public int getHostel_id() {
        return hostel_id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
