package com.example.madee.sga.Admin.Event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.madee.sga.API.EventApi;
import com.example.madee.sga.Dialogs;
import com.example.madee.sga.R;

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

public class AddNewEvent extends AppCompatActivity {
    public EventApi _eventApi;
    Toolbar bar;
    EditText txtEventName, txtEventBrandId, txtEventShopId, txtEventDetails, txtEventDate;
    Button btnAddEvent;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    String StatusCode = "";
    Dialogs dialogs = new Dialogs();

    public AddNewEvent() {
        _eventApi = retrofit.create(EventApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        bar = findViewById(R.id.toolbar2);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtEventName = findViewById(R.id.txtEventName);
        txtEventShopId = findViewById(R.id.txtEventShopId);
        txtEventBrandId = findViewById(R.id.txtEventBrandId);
        txtEventDetails = findViewById(R.id.txtEventDetails);
        txtEventDate = findViewById(R.id.txtEventDate);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("ShopId", txtEventShopId.getText());
                    obj.put("BrandId", txtEventBrandId.getText());
                    obj.put("Name", txtEventName.getText());
                    obj.put("Details", txtEventDetails.getText());
                    obj.put("DateTime", txtEventDate.getText());
                    Dialogs.Loader(AddNewEvent.this, 0);
                    AddEvent(obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void AddEvent(String event) {
        Call<ResponseBody> addEvent = _eventApi.AddEvent(event);
        addEvent.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    StatusCode = response.body().string();
                    if (StatusCode.equals("201")) {
                        Dialogs.Loader(AddNewEvent.this, 1);
                        Dialogs.ShowSuccessDialog("Event Added..!", AddNewEvent.this);
                    } else {
                        Dialogs.Loader(AddNewEvent.this, 1);
                        Dialogs.ShowErrorDialog("Event Not Added..!", AddNewEvent.this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(AddNewEvent.this, 1);
                    Dialogs.ShowErrorDialog("Exception Occured while adding Event ", AddNewEvent.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(AddNewEvent.this, 1);
                Dialogs.ShowErrorDialog("Event Creation Failed", AddNewEvent.this);
            }
        });
    }
}
