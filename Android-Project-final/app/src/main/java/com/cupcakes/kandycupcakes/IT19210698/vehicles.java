package com.cupcakes.kandycupcakes.IT19210698;

import com.google.firebase.database.Exclude;

public class vehicles {
    private String name;
    private Integer passengers;
    private Double price;
    private String transmisson;
    private String imageUrl;
    private String key;
    private Integer bags;
    private String available;

    public vehicles() {
    }

    public vehicles(String name, Integer passengers, Double price, String transmisson, String imageUrl,Integer bags) {
        this.name = name;
        this.passengers = passengers;
        this.price = price;
        this.transmisson = transmisson;
        this.imageUrl = imageUrl;
        this.bags = bags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTransmisson() {
        return transmisson;
    }

    public void setTransmisson(String transmisson) {
        this.transmisson = transmisson;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getBags() {
        return bags;
    }

    public void setBags(Integer bags) {
        this.bags = bags;
    }

    @Exclude
    public String getKey(){
        return key;
    }
    @Exclude
    public void setKey(String key){
        this.key = key;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
