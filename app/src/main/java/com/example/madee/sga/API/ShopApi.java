package com.example.madee.sga.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShopApi {
    @GET("Shops/GetById/{id}")
    Call<ResponseBody> GetShopById(@Path("id") int id);

    @GET("Shops/GetAll/{page}")
    Call<ResponseBody> GetAllShops(@Path("page") int page);

    @Headers({"Content-Type: application/json"})
    @POST("Shops/AddNew")
    Call<ResponseBody> AddShop(@Body String _shop);

    @DELETE("Shops/Delete/{id}")
    Call<ResponseBody> RemoveShop(@Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @PUT("Shops/Update")
    Call<ResponseBody> UpdateShop(@Body String _shop);

}
