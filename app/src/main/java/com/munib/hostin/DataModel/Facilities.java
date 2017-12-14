package com.munib.hostin.DataModel;

import java.io.Serializable;

public class Facilities implements Serializable
{
    int id;
    String name,desc;
    public Facilities(int id,String name,String desc)
    {
        this.id=id;
        this.name=name;
        this.desc=desc;
    }

    public String getName() {
        return name;
    }

    public int getFacilityId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}