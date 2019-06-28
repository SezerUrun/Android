package com.freelancer;

import com.google.gson.annotations.SerializedName;

public class Proje {

    @SerializedName("Header")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("Id")
    private int id;
    @SerializedName("OwnerId")
    private int ownerId;
    @SerializedName("WorkerId")
    private int workerId;

    Proje(String name, String description, int id, int ownerId,int workerId){
        this.name=name;
        this.description=description;
        this.id=id;
        this.ownerId=workerId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
}
