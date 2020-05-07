package com.example.madee.sga.Models;

import com.google.gson.annotations.SerializedName;

public class Shop {
    @SerializedName("shop_id")
    public int shop_id;
    @SerializedName("shop_name")
    public String shop_name;
    @SerializedName("shop_owner")
    public String shop_owner;
    @SerializedName("shop_longitude")
    public double shop_longitude;
    @SerializedName("shop_latitude")
    public double shop_latitude;
    @SerializedName("shop_image")
    public String shop_image;

    public Shop(int shop_id, String shop_name, String shop_owner, double shop_longitude, double shop_latitude, String shop_image) {
    }
    //for post
    public Shop(String shop_name, String shop_owner, double shop_longitude, double shop_latitude, String shop_image) {
    }

    public Shop() {

    }

    // Getters
    public int GetShopId(){
        return shop_id;
    }

    public String GetShopName(){
        return shop_name;
    }

    public String GetOwnerName(){
        return shop_owner;
    }

    public double GetLongitude(){
        return shop_longitude;
    }

    public double GetLatitude(){
        return shop_latitude;
    }

    public String GetImagepath(){
        return shop_image;
    }

    //Setters
    public void SetShopId(int id){
        this.shop_id = id;
    }

    public void SetShopName(String shopName){
        this.shop_name = shopName;
    }

    public void SetOwnerName(String ownerName){
        this.shop_owner = ownerName;
    }

    public void SetLongitude(double longitude){
        this.shop_longitude = longitude;
    }

    public void SetLatitude(double latitude){
        this.shop_latitude = latitude;
    }

    public void SetImagepath(String imagepath){
        this.shop_image = imagepath;
    }

}
