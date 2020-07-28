package com.app.carhak.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSinglePart {

@SerializedName("code")
@Expose
private String code;
@SerializedName("status")
@Expose
private String status;
@SerializedName("data")
@Expose
private List<SinglePartData> data = null;
@SerializedName("RatingList")
@Expose
private List<RatingList> ratingList = null;

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

public List<SinglePartData> getData() {
return data;
}

public void setData(List<SinglePartData> data) {
this.data = data;
}

public List<RatingList> getRatingList() {
return ratingList;
}

public void setRatingList(List<RatingList> ratingList) {
this.ratingList = ratingList;
}

}