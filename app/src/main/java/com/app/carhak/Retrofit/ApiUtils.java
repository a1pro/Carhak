package com.app.carhak.Retrofit;

import android.content.Context;



public class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = "https://carhak.com/";
    public static final String IMAGE_BASE_URL="https://carhak.com/asset/Upload/Vehicle/Image/";
    public static final String IMAGE_PARTS_BASE_URL ="https://carhak.com/asset/Upload/";
    public static final String IMAGE_URL ="https://carhak.com/asset/userProfile/";






    public static ApiInterface getAPIService(Context context) {

            return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);

    }








}