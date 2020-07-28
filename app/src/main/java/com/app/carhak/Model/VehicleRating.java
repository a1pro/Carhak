package com.app.carhak.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleRating {
    @SerializedName("v_rating_id")
    @Expose
    private String vRatingId;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("buyer_id")
    @Expose
    private String buyerId;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("v_rating")
    @Expose
    private String vRating;
    @SerializedName("v_content")
    @Expose
    private String vContent;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;

    public String getVRatingId() {
        return vRatingId;
    }

    public void setVRatingId(String vRatingId) {
        this.vRatingId = vRatingId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVRating() {
        return vRating;
    }

    public void setVRating(String vRating) {
        this.vRating = vRating;
    }

    public String getVContent() {
        return vContent;
    }

    public void setVContent(String vContent) {
        this.vContent = vContent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
