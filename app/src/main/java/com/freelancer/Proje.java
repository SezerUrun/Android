package com.freelancer;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Proje {

    @SerializedName("Header")
    private String header;
    @SerializedName("Description")
    private String description;
    @SerializedName("Id")
    private int id;
    @SerializedName("OwnerId")
    private int ownerId;
    @SerializedName("WorkerId")
    private @Nullable Integer workerId;
    @SerializedName("MaxPrice")
    private int maxPrice;
    @SerializedName("ReleaseTime")
    String releaseTime;
    @SerializedName("DeadlineTime")
    String deadline;
    @SerializedName("IsCompletedOwner")
    Boolean IsCompletedOwner;
    @SerializedName("IsCompletedWorker")
    Boolean IsCompletedWorker;

    Proje(String header, String description, int id, int ownerId,int maxPrice){
        this.header=header;
        this.description=description;
        this.id=id;
        this.ownerId=ownerId;
        this.maxPrice=maxPrice;
        IsCompletedOwner=false;
        IsCompletedWorker=false;
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

    public int getWorkerId() { return workerId; }

    public void setWorkerId(Integer workerId) { this.workerId = workerId; }

    public int getMaxPrice() { return maxPrice; }

    public void setMaxPrice(int maxPrice) { this.maxPrice = maxPrice; }

    public String getReleaseTime() { return releaseTime; }

    public void setReleaseTime(String releaseTime) { this.releaseTime = releaseTime; }

    public String getDeadline() { return deadline; }

    public void setDeadline(String deadline) { this.deadline = deadline; }

    public Boolean getCompletedOwner() { return IsCompletedOwner; }

    public void setCompletedOwner(Boolean IsCompletedOwner) { this.IsCompletedOwner = IsCompletedOwner; }

    public Boolean getCompletedWorker() { return IsCompletedWorker; }

    public void setCompletedWorker(Boolean complateWorker) {this.IsCompletedWorker = complateWorker; }
}
