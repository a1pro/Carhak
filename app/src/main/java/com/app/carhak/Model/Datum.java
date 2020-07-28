package com.app.carhak.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("id")
@Expose
private String id;
@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("last_name")
@Expose
private String lastName;
@SerializedName("email")
@Expose
private String email;
@SerializedName("password")
@Expose
private String password;
@SerializedName("gender")
@Expose
private String gender;
@SerializedName("usertype")
@Expose
private String usertype;
@SerializedName("deviceType")
@Expose
private String deviceType;
@SerializedName("deviceId")
@Expose
private String deviceId;
@SerializedName("status")
@Expose
private String status;
@SerializedName("token")
@Expose
private String token;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
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

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getUsertype() {
return usertype;
}

public void setUsertype(String usertype) {
this.usertype = usertype;
}

public String getDeviceType() {
return deviceType;
}

public void setDeviceType(String deviceType) {
this.deviceType = deviceType;
}

public String getDeviceId() {
return deviceId;
}

public void setDeviceId(String deviceId) {
this.deviceId = deviceId;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

}