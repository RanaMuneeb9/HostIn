package com.munib.hostin.DataModel;

/**
 * Created by MuhammadMusa on 10/24/2017.
 */

public class HostelsData {

    private int img_res;
    private String hostel_name,prices,places;

    public HostelsData(int img_res, String hostel_name, String places, String prices)
    {
        this.setImg_res(img_res);
        this.setHostel_name(hostel_name);
        this.setPlaces(places);
        this.setPrices(prices);


    }

    public int getImg_res() {
        return img_res;
    }

    public void setImg_res(int img_res) {
        this.img_res = img_res;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }
}
