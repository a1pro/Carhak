package com.app.carhak.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartsData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("part_number")
    @Expose
    private String partNumber;
    @SerializedName("year_vehicle")
    @Expose
    private String yearVehicle;
    @SerializedName("vehicle_modal")
    @Expose
    private String vehicleModal;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("Interior")
    @Expose
    private String interior;
    @SerializedName("lamps")
    @Expose
    private String lamps;
    @SerializedName("part_category")
    @Expose
    private String partCategory;
    @SerializedName("part_sub_category")
    @Expose
    private String partSubCategory;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("Average_rating")
    @Expose
    private int averageRating;
    @SerializedName("brand_name")
    @Expose
    private String brandName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getYearVehicle() {
        return yearVehicle;
    }

    public void setYearVehicle(String yearVehicle) {
        this.yearVehicle = yearVehicle;
    }

    public String getVehicleModal() {
        return vehicleModal;
    }

    public void setVehicleModal(String vehicleModal) {
        this.vehicleModal = vehicleModal;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getLamps() {
        return lamps;
    }

    public void setLamps(String lamps) {
        this.lamps = lamps;
    }

    public String getPartCategory() {
        return partCategory;
    }

    public void setPartCategory(String partCategory) {
        this.partCategory = partCategory;
    }

    public String getPartSubCategory() {
        return partSubCategory;
    }

    public void setPartSubCategory(String partSubCategory) {
        this.partSubCategory = partSubCategory;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
