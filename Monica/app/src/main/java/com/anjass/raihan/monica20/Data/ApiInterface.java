package com.anjass.raihan.monica20.Data;

import com.anjass.raihan.monica20.Class.LoginResponse;
import com.anjass.raihan.monica20.Class.UserClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    // String BASEURL = "http://192.168.173.1/monica-api/public/api/";
    // String BASEURL = "http://sawadikap.himatif.org/api/";

    @FormUrlEncoded
    @POST("login")  // API's endpoints
    @Headers("Content-Type: application/json")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password);
}
