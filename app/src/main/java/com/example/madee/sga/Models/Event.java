package com.example.madee.sga.Models;

public class Event {
    public int event_id;
    public int shop_id;
    public int brand_id;
    public String event_name;
    public String event_details;
    public String event_datetime;


    // Getter Methods
    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    // Setter Methods

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_details() {
        return event_details;
    }

    public void setEvent_details(String event_details) {
        this.event_details = event_details;
    }

    public String getEvent_datetime() {
        return event_datetime;
    }

    public void setEvent_datetime(String event_datetime) {
        this.event_datetime = event_datetime;
    }
}
