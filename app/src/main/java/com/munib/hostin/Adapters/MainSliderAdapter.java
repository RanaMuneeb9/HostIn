package com.munib.hostin.Adapters;

import java.util.ArrayList;

import ss.com.bannerslider.SlideType;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    ArrayList<String> images;
    public MainSliderAdapter(ArrayList<String> images)
    {
        this.images=images;
    }
    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide("http://13.127.35.98/Api/images/"+images.get(0));
                break;
            case 1:
                viewHolder.bindImageSlide("http://13.127.35.98/Api/images/"+images.get(1));
                 break;
            case 2:
                viewHolder.bindImageSlide("http://13.127.35.98/Api/images/"+images.get(2));
                break;
            case 3:
                viewHolder.bindImageSlide("http://13.127.35.98/Api/images/"+images.get(3));
                break;
            case 4:
                viewHolder.bindImageSlide("http://13.127.35.98/Api/images/"+images.get(4));
                break;
        }
    }
}