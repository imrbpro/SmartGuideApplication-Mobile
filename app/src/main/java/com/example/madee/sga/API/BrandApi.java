package com.example.madee.sga.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BrandApi {

    @GET("Brand/GetAll/{page}")
    Call<ResponseBody> GetAllBrands(@Path("page") int page);

    @GET("Brand/GetById/{id}")
    Call<ResponseBody> GetBrandById(@Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @POST("Brand/AddNew")
    Call<ResponseBody> AddBrand(@Body String _brand);

    @Headers({"Content-Type: application/json"})
    @POST("Brand/Update")
    Call<ResponseBody> UpdateBrand(@Body String _brand);

    @DELETE("Brand/Delete")
    Call<ResponseBody> DeleteBrand(@Query("id") int id);


}
