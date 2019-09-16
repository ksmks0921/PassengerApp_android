package com.yjm.passengerapp;
import com.google.gson.JsonObject;
import com.yjm.passengerapp.util.Constants;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    /*

    @FormUrlEncoded
    @POST("oauth/access_token")
    Call<AccessToken> getAccessToken(
            @Field("code") String code,
            @Field("type") String grantType,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("redirect_uri") String redirect_uri);

    @GET("v2.5/me")
    Call<SocialModule> getProfileFB(@Query("access_token") String access_token,
                                    @Query("fields") String fields);

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<AccessToken> getAccessTokenGG(
            @Field("code") String code,
            @Field("grant_type") String grantType,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("redirect_uri") String redirect_uri);


    @GET("oauth2/v1/userinfo")
    Call<SocialModule> getProfileGG(@Query("access_token") String access_token, @Query("alt") String format);

    @POST("mobile")
    Call<NormalResponseModule> phone_verify(@Query(Constant.TYPE) String type,
                                            @Query(Constant.USER_TYPE) String user_type,
                                            @Query(Constant.USER_PHONENUMBER) String phonenumber);

    @POST("mobile")
    Call<NormalResponseModule> forgot_phone(@Query(Constant.TYPE) String type,
                                            @Query(Constant.USER_PHONENUMBER) String phonenumber);

    @POST("mobile")
    Call<NormalResponseModule> verify_code(@Query(Constant.TYPE) String type,
                                           @Query(Constant.USER_TYPE) String user_type,
                                           @Query(Constant.USER_PHONENUMBER) String phonenumber,
                                           @Query(Constant.USER_VERIFY_CODE) String code);

    @POST("mobile")
    Call<NormalResponseModule> forgot_verify(@Query(Constant.TYPE) String type,
                                             @Query(Constant.USER_PHONENUMBER) String phonenumber,
                                             @Query(Constant.USER_VERIFY_CODE) String code);

    @POST("mobile")
    Call<NormalResponseModule> resend_verify(@Query(Constant.TYPE) String type,
                                             @Query(Constant.USER_TYPE) String user_type,
                                             @Query(Constant.USER_PHONENUMBER) String phonenumber);

    @POST("mobile")
    Call<NormalResponseModule> resend_forgot(@Query(Constant.TYPE) String type,
                                             @Query(Constant.USER_PHONENUMBER) String phonenumber);

    @POST("mobile")
    Call<LoginResultModule> login(@Query(Constant.TYPE) String type,
                                  @Query(Constant.USER_PHONENUMBER) String user_phone,
                                  @Query(Constant.USER_TYPE) String user_type,
                                  @Query(Constant.USER_PASSWORD) String password);

    @POST("mobile")
    Call<NormalResponseModule> register_email(@Query(Constant.TYPE) String type,
                                              @Query(Constant.USER_TYPE) String user_type,
                                              @Query(Constant.USER_PHONENUMBER) String phonenumber,
                                              @Query(Constant.USER_EMAIL) String email);

    @POST("mobile")
    Call<NormalResponseModule> reigster_password(@Query(Constant.TYPE) String type,
                                                 @Query(Constant.USER_PHONENUMBER) String phonenumber,
                                                 @Query(Constant.USER_PASSWORD) String password);

    @POST("mobile")
    Call<NormalResponseModule> forgot_new_pass(@Query(Constant.TYPE) String type,
                                               @Query(Constant.USER_PHONENUMBER) String phonenumber,
                                               @Query(Constant.USER_PASSWORD) String password);

    @POST("mobile")
    Call<LoginResultModule> register_name(@Query(Constant.TYPE) String type,
                                          @Query(Constant.USER_PHONENUMBER) String phonenumber,
                                          @Query(Constant.USER_NAME_FIRST) String firstName,
                                          @Query(Constant.USER_NAME_LAST) String lastName);

    @POST("payment")
    Call<NormalResponseModule> send_token(@Query(Constant.TYPE) String type,
                                          @Query(Constant.USER_EMAIL) String email,
                                          @Query(Constant.USER_ID) String user_id,
                                          @Query(Constant.STRIPE_TOKEN) String tokenID);

    @Multipart
    @POST("mobile")
    Call<LoginResultModule> handy_avatar(@Part("file\"; filename=\"avatar.png\"; name=\"avatar\" ") RequestBody file ,
                                         @Query(Constant.TYPE) String type,
                                         @Query(Constant.JOB_USER) String job_user,
                                         @Query(Constant.HANDY_VEHICLE_TYPE) String vehicle_type,
                                         @Query(Constant.HANDY_VEHICLE_COLOR) String vehicle_color,
                                         @Query(Constant.HANDY_PLATE_NUMBER) String number
    );

    @POST("payment")
    Call<NormalResponseModule> send_handy_token(@Query(Constant.TYPE) String type,
                                                @Query(Constant.USER_EMAIL) String email,
                                                @Query(Constant.USER_ID) String user_id,
                                                @Query(Constant.STRIPE_TOKEN) String token,
                                                @Query(Constant.JOB_LINE1) String line,
                                                @Query(Constant.JOB_CITY) String city,
                                                @Query(Constant.JOB_ZIP) String zip,
                                                @Query(Constant.JOB_STATE) String state,
                                                @Query(Constant.DOB_DD) String dd,
                                                @Query(Constant.DOB_MM) String mm,
                                                @Query(Constant.DOB_YYYY) String yyyy,
                                                @Query(Constant.BILLING_COUNTRY) String countryCode,
                                                @Query(Constant.USER_NAME_FIRST) String name_first,
                                                @Query(Constant.USER_NAME_LAST) String name_last,
                                                @Query(Constant.SSN_NUMBER) String ssn);

    @POST("mobile")
    Call<NormalResponseModule> get_handy_avatar(@Query(Constant.TYPE) String type,
                                                @Query(Constant.JOB_USER) String user_id);

    @POST("mobile")
    Call<NormalResponseModule> push_personal(@Query(Constant.TYPE) String type,
                                             @Query(Constant.JOB_USER) String user_id,
                                             @Query(Constant.PUSH_ID) String push_id);

    @POST("mobile")
    Call<NormalResponseModule> job_location(@Query(Constant.TYPE) String type,
                                            @Query(Constant.JOB_USER) String user_id,
                                            @Query(Constant.JOB_LATI) String lati,
                                            @Query(Constant.JOB_LONGI) String longi);

    @POST("mobile")
    Call<AroundHandymenModelResponse> get_handymen(@Query(Constant.TYPE) String type,
                                                   @Query(Constant.JOB_USER) String user_id);

    @POST("mobile")
    Call<HiredProfileModule> hired_profile(@Query(Constant.TYPE) String type,
                                           @Query(Constant.JOB_CUSTOMER) String user_id);

    @POST("payment")
    Call<ResponseBody> get_payment_added_status(@Query(Constant.TYPE) String type,
                                                @Query(Constant.STRIPE_USER) String user_id);

    @POST("payment")
    Call<ResponseBody> remove_payment_source(@Query(Constant.TYPE) String type,
                                             @Query(Constant.STRIPE_USER) String user_id);

    @POST("payment")
    Call<EarningModuleResponse> get_customer_charging(@Query(Constant.TYPE) String type,
                                                      @Query(Constant.JOB_CUSTOMER) String user_id);

    @POST("mobile")
    Call<JobPendingResponse> get_pending_job(@Query(Constant.TYPE) String type,
                                             @Query(Constant.JOB_CUSTOMER) String uer_id);

    @POST("mobile")
    Call<JobPendingResponse>  get_running_job(@Query(Constant.TYPE) String type,
                                              @Query(Constant.JOB_CUSTOMER) String user_id);

    @POST("mobile")
    Call<HiredProfileModule> confirm_job(@Query(Constant.TYPE) String type,
                                         @Query("id") String job_id,
                                         @Query(Constant.JOB_CUSTOMER) String user_id,
                                         @Query(Constant.JOB_CONTACT) String name,
                                         @Query(Constant.JOB_LINE1) String line1,
                                         @Query(Constant.JOB_LINE2) String line2,
                                         @Query(Constant.JOB_CITY) String city,
                                         @Query(Constant.JOB_STATE) String state,
                                         @Query(Constant.JOB_ZIP) String zip_code,
                                         @Query(Constant.JOB_DESCRIPTION) String description,
                                         @Query(Constant.JOB_CONFIRM) String confirm,
                                         @Query(Constant.JOB_LATI) String lati,
                                         @Query(Constant.JOB_LONGI) String longi);

    @POST("mobile")
    Call<ResponseBody> restore_job_failed(@Query(Constant.TYPE) String type,
                                          @Query("id") String job_id);

    @POST("mobile")
    Call<NormalResponseModule> end_job(@Query(Constant.TYPE) String type,
                                       @Query(Constant.JOB_CUSTOMER) String user_id,
                                       @Query(Constant.JOB_HANDYMAN) String handy_id,
                                       @Query(Constant.JOB_COST) String cost,
                                       @Query(Constant.JOB_ID) String job_id,
                                       @Query(Constant.JOB_RATE) String score,
                                       @Query(Constant.JOB_REVIEW_DETAIL) String review,
                                       @Query(Constant.JOB_FROM_CUSTOMER) String from);

    @POST("mobile")
    Call<HiredProfileModule> job_order(@Query(Constant.TYPE) String type,
                                       @Query(Constant.JOB_CUSTOMER) String user_id,
                                       @Query(Constant.JOB_CONTACT) String name,
                                       @Query(Constant.JOB_LINE1) String line1,
                                       @Query(Constant.JOB_LINE2) String line2,
                                       @Query(Constant.JOB_CITY) String city,
                                       @Query(Constant.JOB_STATE) String state,
                                       @Query(Constant.JOB_ZIP) String zip_code,
                                       @Query(Constant.JOB_DESCRIPTION) String description,
                                       @Query(Constant.JOB_CONFIRM) String confirm,
                                       @Query(Constant.JOB_LATI) String lati,
                                       @Query(Constant.JOB_LONGI) String longi
    );

    @Multipart
    @POST("mobile")
    Call<NormalResponseModule> upload_avatar(@Part("file\"; filename=\"avatar.png\"; name=\"avatar\" ") RequestBody file ,
                                             @Query(Constant.TYPE) String type,
                                             @Query(Constant.JOB_USER) String job_user);

    @POST("mobile")
    Call<NormalResponseModule> push_personal_handy(@Query(Constant.TYPE) String type,
                                                   @Query(Constant.JOB_USER) String user_id,
                                                   @Query(Constant.PUSH_ID) String push_id,
                                                   @Query(Constant.IS_HANDYMAN) String handy);

    @POST("mobile")
    Call<NormalResponseModule> handy_status(@Query(Constant.TYPE) String type,
                                            @Query(Constant.JOB_USER) String user_id,
                                            @Query(Constant.HANDYMAN_ONLINE) String onine);

    @POST("payment")
    Call<EarningModuleResponse> get_handy_earning(@Query(Constant.TYPE) String type,
                                                  @Query(Constant.JOB_HANDYMAN) String user_id);

    @POST("mobile")
    Call<HandyRateResponse> get_handy_review(@Query(Constant.TYPE) String type,
                                             @Query(Constant.JOB_HANDYMAN) String user_id);

    @POST("mobile")
    Call<JobPendingResponse> get_handy_hired_job(@Query(Constant.TYPE) String type,
                                                 @Query(Constant.JOB_HANDYMAN) String user_id);

    @POST("mobile")
    Call<JobPendingModule> get_detail_job(@Query(Constant.TYPE) String type,
                                          @Query(Constant.JOB_ID) String job_id);

    @POST("mobile")
    Call<NormalResponseModule> give_review_customer(@Query(Constant.TYPE) String type,
                                                    @Query(Constant.JOB_CUSTOMER) String customer_id,
                                                    @Query(Constant.JOB_HANDYMAN) String user_id,
                                                    @Query(Constant.JOB_ID) String job_id,
                                                    @Query(Constant.JOB_RATE) String rate,
                                                    @Query(Constant.JOB_REVIEW_DETAIL) String review);

    @POST("mobile")
    Call<NormalResponseModule> handy_job_location(@Query(Constant.TYPE) String type,
                                                  @Query(Constant.JOB_USER) String user_id,
                                                  @Query(Constant.JOB_LATI) String lati,
                                                  @Query(Constant.JOB_LONGI) String longi,
                                                  @Query(Constant.IS_HANDYMAN) String isHandy);

    @POST("mobile")
    Call<JobPendingModule> get_accept_job(@Query(Constant.TYPE) String type,
                                          @Query(Constant.JOB_HANDYMAN) String user_id);

    @POST("json")
    Call<ResponseBody> getRoute(@Query("origin") String origin,
                                @Query("destination") String destination,
                                @Query("mode") String mode);

    @POST("call/accessToken")
    Call<ResponseBody> get_twillio_token(@Query("userid") String user_id);

    @POST("payment")
    Call<ResponseBody> get_payment_added_status_handy(@Query(Constant.TYPE) String type,
                                                      @Query(Constant.STRIPE_USER) String user_id);
    */


    //get the passengerShift data
    @GET("api/Passenger/getPassengerShift")
    @Headers({"Content-Type: application/json", "Authorization:bearer  Duij9Ewne_O9J71TMCdjUcUCJrZ2iREQwfDdL9j9PkTKyuRUMnyzVeRFmRjOWSOZt0U8tUyUUuc43w5ekOBAlYAPdKKPP8drJyxFG-WbAS05n1lqBW5VHClPMQaMGYEZCUa83B9EptJf5XP6jleIa4eM7651V2LJLfG8wqYhVueGGy-88spxKglcWvihXDoQsEEMGmNXLFlADLPWBpKuYG6PcLkwjpW3lnbFXPR4qmaUGgsmkYKNLbGDKu5DNQH7z6zcsSVfXGmzEQtteoFWzP9ZhA0uijECsNk4qIs1dDBvMEiFZagbR_hMffFfF_2A"})
    Call<JsonObject> getShiftsofWeek();

    @GET("api/Passenger/getShiftOfDate?dt=2018-06-24")
    @Headers({"Content-Type: application/json", "Authorization:bearer  Duij9Ewne_O9J71TMCdjUcUCJrZ2iREQwfDdL9j9PkTKyuRUMnyzVeRFmRjOWSOZt0U8tUyUUuc43w5ekOBAlYAPdKKPP8drJyxFG-WbAS05n1lqBW5VHClPMQaMGYEZCUa83B9EptJf5XP6jleIa4eM7651V2LJLfG8wqYhVueGGy-88spxKglcWvihXDoQsEEMGmNXLFlADLPWBpKuYG6PcLkwjpW3lnbFXPR4qmaUGgsmkYKNLbGDKu5DNQH7z6zcsSVfXGmzEQtteoFWzP9ZhA0uijECsNk4qIs1dDBvMEiFZagbR_hMffFfF_2A"})
    Call<JsonObject> getShiftOfDate();

    // login
    @FormUrlEncoded
    @POST("api/auth")
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> login(@Field("username") String userName, @Field("password") String userPassword, @Field("grant_type") String grantType);

    //verify the code
    @FormUrlEncoded
    @POST("api/auth")
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> verify(@Field("username") String userName, @Field("password") String userPassword, @Field("grant_type") String grantType);

    //getting bus location
    @GET("api/Passenger/getPassengerBusLocation")
    @Headers({"Content-Type: application/json", "Authorization:bearer  Duij9Ewne_O9J71TMCdjUcUCJrZ2iREQwfDdL9j9PkTKyuRUMnyzVeRFmRjOWSOZt0U8tUyUUuc43w5ekOBAlYAPdKKPP8drJyxFG-WbAS05n1lqBW5VHClPMQaMGYEZCUa83B9EptJf5XP6jleIa4eM7651V2LJLfG8wqYhVueGGy-88spxKglcWvihXDoQsEEMGmNXLFlADLPWBpKuYG6PcLkwjpW3lnbFXPR4qmaUGgsmkYKNLbGDKu5DNQH7z6zcsSVfXGmzEQtteoFWzP9ZhA0uijECsNk4qIs1dDBvMEiFZagbR_hMffFfF_2A"  })
    Call<JsonObject> getBusLocation();



}
