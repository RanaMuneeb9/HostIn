package com.munib.hostin.DataModel;

/**
 * Created by rana_ on 10/29/2017.
 */

public class PaymentsData {

    int id,user_id,hostel_id,amount;
    String name,desc,status,created_date,paid_date,hostel_name,hostel_email;

    public PaymentsData(int id,int user_id,int hostel_id,String hostel_name,int amount,String name,String desc,String status,String created_date,String paid_date,String hostel_email)
    {
        this.id=id;
        this.user_id=user_id;
        this.hostel_id=hostel_id;
        this.amount=amount;
        this.status=status;
        this.desc=desc;
        this.name=name;
        this.created_date=created_date;
        this.paid_date=paid_date;
        this.hostel_name=hostel_name;
        this.hostel_email=hostel_email;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getHostel_id() {
        return hostel_id;
    }

    public String getHostel_email() {
        return hostel_email;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getPaid_date() {
        return paid_date;
    }

    public String getStatus() {
        return status;
    }
}
