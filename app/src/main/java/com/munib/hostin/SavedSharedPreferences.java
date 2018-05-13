package com.munib.hostin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

public class SavedSharedPreferences
{

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    public static void setCurrentHostelId(Context ctx, int id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt("hostel_id", id);
        editor.commit();
    }
    public static void setLeaveRequest(Context ctx, int id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt("leave_request", id);
        editor.commit();
    }
    public static void setRating(Context ctx, int rating)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt("rating", rating);
        editor.commit();
    }
    public static void setMinPrice(Context ctx, String min)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("min_price", min);
        editor.commit();
    }

    public static void setMaxPrice(Context ctx, String min)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("max_price", min);
        editor.commit();
    }

    public static void setUserId(Context ctx,int id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt("user_id", id);
        editor.commit();
    }

    public static void setUserName(Context ctx,String name)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("user_name", name);
        editor.commit();
    }
    public static void setUserLat(Context ctx,String lat)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("user_lat", lat);
        editor.commit();
    }
    public static void setUserLang(Context ctx,String lang)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("user_lang", lang);
        editor.commit();
    }
    public static void setUserGender(Context ctx,String gender)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("user_gender", gender);
        editor.commit();
    }
    public static void setUserImage(Context ctx,String image)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("user_image", image);
        editor.commit();
    }
    public static void setUserEmail(Context ctx,String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("user_email", email);
        editor.commit();
    }
    public static void setCustomRadius(Context ctx,int radius)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt("custom_radius",radius);
        editor.commit();
    }
    public static void setMobile(Context ctx,String phone)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("user_mobile",phone);
        editor.commit();
    }
    public static String getMobileNo(Context ctx)
    {
        return getSharedPreferences(ctx).getString("user_mobile","");
    }
    public static int getCustomRadius(Context ctx)
    {
        return getSharedPreferences(ctx).getInt("custom_radius",100);
    }
    public static int getCurrentHostelId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt("hostel_id",0);
    }
    public static int getLeaveRequest(Context ctx)
    {
        return getSharedPreferences(ctx).getInt("leave_request",0);
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString("user_name", "null");
    }
    public static int getUserId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt("user_id", 0);
    }
    public static int getRating(Context ctx)
    {
        return getSharedPreferences(ctx).getInt("rating", 0);
    }
    public static String getMinPrice(Context ctx)
    {
        return getSharedPreferences(ctx).getString("min_price", "0.0");
    }
    public static String getMaxPrice(Context ctx)
{
    return getSharedPreferences(ctx).getString("max_price", "0.0");
}
    public static String getUserLat(Context ctx)
    {
        return getSharedPreferences(ctx).getString("user_lat", "0.0");
    }
    public static String getUserLang(Context ctx)
    {
        return getSharedPreferences(ctx).getString("user_long", "0.0");
    }
    public static String getUserGender(Context ctx)
    {
        return getSharedPreferences(ctx).getString("user_gender", "null");
    }
    public static String getUserEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString("user_email", "null");
    }
    public static String getUserImage(Context ctx)
    {
        return getSharedPreferences(ctx).getString("user_image", "null");
    }


    public static void clear(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}