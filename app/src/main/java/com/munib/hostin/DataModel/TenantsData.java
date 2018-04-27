package com.munib.hostin.DataModel;

/**
 * Created by rana_ on 3/20/2018.
 */

public class TenantsData {
    int count, hostel_id;
    public TenantsData(int count,int hostel_id)
    {
        this.count=count;
        this.hostel_id=hostel_id;
    }

    public int getHostel_id() {
        return hostel_id;
    }

    public int getCount() {
        return count;
    }
}
