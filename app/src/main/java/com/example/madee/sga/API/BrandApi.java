package com.example.madee.sga.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BrandApi {

    @GET("/api/Brand/GetAll/{page}")
    Call<ResponseBody> GetAllBrands(@Path("page") int page);

    @GET("/api/Brand/GetById/{id}")
    Call<ResponseBody> GetBrandById(@Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @POST("/api/Brand/AddNew")
    Call<ResponseBody> AddBrand(@Body String _brand);

    @Headers({"Content-Type: application/json"})
    @PUT("/api/Brand/AddNew")
    Call<ResponseBody> UpdateBrand(@Body String _brand);


}
