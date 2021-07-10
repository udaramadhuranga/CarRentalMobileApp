package com.cupcakes.kandycupcakes.IT19207100;

import com.google.firebase.database.Exclude;

public class Upload {

    private String mName;
    private String mImageUrl;
    private  String mKey;
    private int mPassengers;
    private int bags;
    private double mprice;
    private String transmisson;
    private String available;



    public Upload() {
        //empty constructor needed
    }


    public Upload(String name, String imageUrl,int passengers,int bag ,double price,String Trans,String avail) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        mPassengers=passengers;
        mprice=price;
        transmisson=Trans;
        bags=bag;
        available=avail;
    }
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public int getPassengers() {
        return mPassengers;
    }

    public void setPassengers(int passengers) {
        mPassengers = passengers;
    }

    public double getPrice() {
        return mprice;
    }

    public void setPrice(double price) {
        this.mprice = price;
    }

    public String getTransmisson() {
        return transmisson;
    }

    public void setTransmisson(String transmisson) {
        this.transmisson = transmisson;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Exclude
    public String getKey(){
        return mKey;
    }
    @Exclude
    public void setKey(String key){
        mKey=key;
    }
}
