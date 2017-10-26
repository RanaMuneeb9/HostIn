package com.munib.hostin.DataModel;

/**
 * Created by MuhammadMusa on 10/26/2017.
 */

public class SavedHostelData {

    private int img_res1;
    private String h_name,p_name,pay;

    public SavedHostelData(int img_res1, String h_name, String p_name, String pay)
    {
        this.setImg_res1(img_res1);
        this.setH_name(h_name);
        this.setP_name(p_name);
        this.setPay(pay);

    }

    public int getImg_res1() {
        return img_res1;
    }

    public void setImg_res1(int img_res1) {
        this.img_res1 = img_res1;
    }

    public String getH_name() {
        return h_name;
    }

    public void setH_name(String h_name) {
        this.h_name = h_name;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
