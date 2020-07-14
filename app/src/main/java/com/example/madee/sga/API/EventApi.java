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

public interface EventApi {

    @GET("Event/GetAll/{page}")
    Call<ResponseBody> GetAllEvents(@Path("page") int page);

    @GET("Event/GetById/{id}")
    Call<ResponseBody> GetEventById(@Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @POST("Event/Add")
    Call<ResponseBody> AddEvent(@Body String _event);

    @Headers({"Content-Type: application/json"})
    @PUT("Event/Update")
    Call<ResponseBody> UpdateEvent(@Body String _event);

    @DELETE("Event/Delete/{id}")
    Call<ResponseBody> DeleteEvent(@Path("id") int id);


}
