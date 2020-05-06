package com.example.madee.sga.API;

import com.example.madee.sga.Models.Shop;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShopApi {
    @GET("Shops/GetAll/{page}")
    Call<List<Shop>> GetAllShops(@Path("page") int page );


    @Headers({"Content-Type: application/json"})
    @POST("Shops/AddNew")
    Call<ResponseBody> AddShop(@Body Shop _shop);

}
