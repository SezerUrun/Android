package com.freelancer;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Proje {

    @SerializedName("Header")
    private String header;
    @SerializedName("Description")
    private String description;
    @SerializedName("ProjectId")
    private int id;
    @SerializedName("OwnerId")
    private int ownerId;
    @SerializedName("WorkerId")
    private @Nullable Integer workerId;
    @SerializedName("MaxPrice")
    private int maxPrice;

    Proje(String header, String description, int id, int ownerId,int workerId,int maxPrice){
        this.header=header;
        this.description=description;
        this.id=id;
        this.workerId=workerId;
        this.ownerId=ownerId;
        this.maxPrice=maxPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setName(String header) {
        this.header = header;
    }

    public void setHeader(String header) { this.header = header; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public int getMaxPrice() { return maxPrice; }

    public void setMaxPrice(int maxPrice) { this.maxPrice = maxPrice; }
}
