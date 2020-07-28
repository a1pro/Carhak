package com.app.carhak.Retrofit;


import com.app.carhak.Model.ForgotPasswordModel;
import com.app.carhak.Model.GetAllBrand;
import com.app.carhak.Model.GetAllBrandCars;
import com.app.carhak.Model.GetAllCategories;
import com.app.carhak.Model.GetAllParts;
import com.app.carhak.Model.GetNewCars;
import com.app.carhak.Model.GetSinglePart;
import com.app.carhak.Model.GetSingleVehicle;
import com.app.carhak.Model.GetSubPart;
import com.app.carhak.Model.LoginModel;
import com.app.carhak.Model.ResponseData;
import com.app.carhak.Model.ViewProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/Buyer-Login")
    Call<LoginModel> LoginApi(@Field("email") String email,
                              @Field("password") String password,
                              @Field("deviceId") String deviceId,
                              @Field("deviceType") String deviceType);

    @FormUrlEncoded
    @POST("api/Buyer-register")
    Call<ResponseData> RegisterApi(@Field("email") String email,
                                   @Field("password") String password,
                                   @Field("firstName") String first_name,
                                   @Field("mobileNo") String phone_no);


    @GET("api/Buyer-getAllNewCarBrandForSale")
    Call<GetAllBrandCars> AllNewBrandCars();

    @FormUrlEncoded
    @POST("api/Buyer-getAllPart")
    Call<GetAllParts> GetAllParts(@Field("year_vehicle") String email,
                                  @Field("vehicle_modal") String password,
                                  @Field("part_category") String first_name,
                                  @Field("zipcode") String zipcode,
                                  @Field("sub_part_category") String sub_part_category);


    @FormUrlEncoded
    @POST("api/Buyer-forgot-PasswordApi")
    Call<ForgotPasswordModel> ForgotPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST("api/Buyer-changePass")
    Call<ResponseData> ChangePassword(@Field("otp") String otp,
                                      @Field("password") String password,
                                      @Field("confirm_password") String confirm_password);



    @FormUrlEncoded
    @POST("api/Buyer-getAllNewCarForSale")
    Call<GetNewCars> AllNewCarforSale(@Field("year_vehicle") String year_vehicle,
                                      @Field("brand_vehicle") String brand_vehicle,
                                      @Field("zipcode") String zipcode);



    @FormUrlEncoded
    @POST("api/Buyer-getProfile")
    Call<ViewProfile> GetProfile(@Field("userId") String userId);




    @Multipart
    @POST("api/Buyer-editPorfile")
    Call<ResponseData> EditProfile(
            @Part("userId") RequestBody userId,
            @Part("first_name") RequestBody first_name,
            @Part("full_address") RequestBody full_address,
            @Part("zipcode") RequestBody zipcode,
            @Part("phone_no") RequestBody phone_no,
            @Part MultipartBody.Part profile_img);




    @GET("api/Buyer-getAllBrands")
    Call<GetAllBrand> AllBrand();

    @GET("api/Buyer-getAllPartCategories")
    Call<GetAllCategories> AllCategories();


    @FormUrlEncoded
    @POST("api/Buyer-getSinglePartDetail")
    Call<GetSinglePart> SinglePartDetail(@Field("partId") String partId);

    @FormUrlEncoded
    @POST("api/Buyer-getSingleVehicleDetail")
    Call<GetSingleVehicle> SingleVehicleDetail(@Field("vehicleId") String partId);


    @FormUrlEncoded
    @POST("api/Buyer-buyerLogout")
    Call<ResponseData> Logout(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("api/Buyer-getSubPartslist")
    Call<GetSubPart> SubPart(@Field("partId") String partId);




}
