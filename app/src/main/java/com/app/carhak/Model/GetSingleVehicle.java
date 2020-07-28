package com.app.carhak.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSingleVehicle {

@SerializedName("code")
@Expose
private String code;
@SerializedName("status")
@Expose
private String status;
@SerializedName("data")
@Expose
private List<SingleVehicleData> data = null;
@SerializedName("RatingList")
@Expose
private List<VehicleRating> ratingList = null;

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public List<SingleVehicleData> getData() {
return data;
}

public void setData(List<SingleVehicleData> data) {
this.data = data;
}

public List<VehicleRating> getRatingList() {
return ratingList;
}

public void setRatingList(List<VehicleRating> ratingList) {
this.ratingList = ratingList;
}

}