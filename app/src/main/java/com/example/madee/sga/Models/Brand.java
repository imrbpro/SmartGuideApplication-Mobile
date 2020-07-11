package com.example.madee.sga.Models;

public class Brand {
    public int brand_id;
    public String brand_name;
    public String shop_no;
    public String brand_owner;
    public String brand_logo;


    // Getter Methods

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getShop_no() {
        return shop_no;
    }

    // Setter Methods

    public void setShop_no(String shop_no) {
        this.shop_no = shop_no;
    }

    public String getBrand_owner() {
        return brand_owner;
    }

    public void setBrand_owner(String brand_owner) {
        this.brand_owner = brand_owner;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

}
