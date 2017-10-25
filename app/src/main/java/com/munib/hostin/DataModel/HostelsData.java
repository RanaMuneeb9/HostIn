package com.munib.hostin.DataModel;

/**
 * Created by MuhammadMusa on 10/24/2017.
 */

public class HostelsData {

    private int img_res;
    private String f_name,d_name,price;

    public HostelsData(int img_res, String f_name, String d_name, String price)
    {
        this.setImg_res(img_res);
        this.setF_name(f_name);
        this.setD_name(d_name);
        this.setPrice(price);

    }

    public int getImg_res() {
        return img_res;
    }

    public void setImg_res(int img_res) {
        this.img_res = img_res;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
