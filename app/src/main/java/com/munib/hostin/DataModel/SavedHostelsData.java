package com.munib.hostin.DataModel;

/**
 * Created by rana_ on 3/6/2018.
 */

public class SavedHostelsData {
    int save_id,hostel_id,user_id;

    public SavedHostelsData(int save_id,int hostel_id,int user_id)
    {
        this.save_id=save_id;
        this.hostel_id=hostel_id;
        this.user_id=user_id;
    }
    public int getUser_id() {
        return user_id;
    }

    public int getHostel_id() {
        return hostel_id;
    }

    public int getSave_id() {
        return save_id;
    }
}
