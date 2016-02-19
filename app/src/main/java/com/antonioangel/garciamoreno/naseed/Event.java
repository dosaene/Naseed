package com.antonioangel.garciamoreno.naseed;

import android.graphics.Bitmap;

public class Event {

    protected String date;
    protected String fullname;
    protected String image;
    protected Bitmap btmp;

    public Event (String date, String fullname, String image){
        this.date = date;
        this.fullname = fullname;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getBtmp() {
        return btmp;
    }

    public void setBtmp(Bitmap btmp) {
        this.btmp = btmp;
    }

}
