package com.freelancer;

import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("Id")
    int id;
    @SerializedName("UserId")
    int userId;
    @SerializedName("ProjectId")
    int projectId;
    @SerializedName("OfferPrice")
    int offerPrice;
    @SerializedName("Description")
    String offerDescription;

    Offer(int projectId, int userId, int offerPrice, String offerDescription){
        this.userId=userId;
        this.projectId=projectId;
        this.offerPrice=offerPrice;
        this.offerDescription=offerDescription;
    }

    public int getId() { return id; }

    public void setId(int id){ this.id=id;}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(int offerPrice) {
        this.offerPrice = offerPrice;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getWorkerId() { return userId; }

    public void setWorkerId(int workerId) { this.userId = workerId; }

    public String getOfferDescription() { return offerDescription; }

    public void setOfferDescription(String offerDescription) { this.offerDescription = offerDescription; }
}
