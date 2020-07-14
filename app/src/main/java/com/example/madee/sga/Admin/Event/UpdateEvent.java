package com.example.madee.sga.Admin.Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.madee.sga.API.EventApi;
import com.example.madee.sga.Dialogs;
import com.example.madee.sga.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpdateEvent extends AppCompatActivity {


    EditText txtEventName, txtEventBrandId, txtEventShopId, txtEventDetails, txtEventDate;
    Button btnUpdateEvent;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    EventApi _eventApi;

    int EventId = 0;
    String Response = "";

    Dialogs dialogs = new Dialogs();

    public UpdateEvent() {
        _eventApi = retrofit.create(EventApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        Intent intent = getIntent();
        EventId = intent.getIntExtra("EventId", 0);

        txtEventName = findViewById(R.id.txtEventName);
        txtEventShopId = findViewById(R.id.txtEventShopId);
        txtEventBrandId = findViewById(R.id.txtEventBrandId);
        txtEventDetails = findViewById(R.id.txtEventDetails);
        txtEventDate = findViewById(R.id.txtEventDate);
        btnUpdateEvent = findViewById(R.id.btnUpdateEvent);

        Dialogs.Loader(this, 0);
        GetEventById(EventId);

        btnUpdateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.Loader(UpdateEvent.this, 0);
                JSONObject obj = new JSONObject();
                try {
                    obj.put("Id", EventId);
                    obj.put("ShopId", txtEventShopId.getText());
                    obj.put("BrandId", txtEventBrandId.getText());
                    obj.put("Name", txtEventName.getText());
                    obj.put("Details", txtEventDetails.getText());
                    obj.put("DateTime", txtEventDate.getText());
                    Dialogs.Loader(UpdateEvent.this, 0);
                    UpdateEvent(obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void UpdateView(String name, String ShopId, String BrandId, String Details, String Date) {
        txtEventName.setText(name);
        txtEventShopId.setText(ShopId);
        txtEventBrandId.setText(BrandId);
        txtEventDetails.setText(Details);
        txtEventDate.setText(Date);
    }

    public void GetEventById(int id) {
        Call<ResponseBody> getEvent = _eventApi.GetEventById(id);
        getEvent.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Response = response.body().string();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    com.example.madee.sga.Models.Event[] event = gson.fromJson(Response, com.example.madee.sga.Models.Event[].class);
                    UpdateView(event[0].Name, String.valueOf(event[0].ShopId), String.valueOf(event[0].BrandId), event[0].Details, event[0].DateTime);
                    Dialogs.Loader(UpdateEvent.this, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(UpdateEvent.this, 1);
                    Dialogs.ShowErrorDialog("Error Occured..!", UpdateEvent.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(UpdateEvent.this, 1);
                Dialogs.ShowErrorDialog("Error Occured..!", UpdateEvent.this);
            }
        });
    }

    public void UpdateEvent(String event) {
        Call<ResponseBody> update = _eventApi.UpdateEvent(event);
        update.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body().string() == "200")
                        Dialogs.Loader(UpdateEvent.this, 1);
                    Dialogs.ShowSuccessDialog("Event Updated Successfully", UpdateEvent.this);
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(UpdateEvent.this, 1);
                    Dialogs.ShowErrorDialog("Error: Event Not Updated", UpdateEvent.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(UpdateEvent.this, 1);
                Dialogs.ShowErrorDialog("Error: Event Not Updated", UpdateEvent.this);
            }
        });
    }
}