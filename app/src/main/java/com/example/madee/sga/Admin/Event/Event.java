package com.example.madee.sga.Admin.Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.madee.sga.API.EventApi;
import com.example.madee.sga.Adapters.EventAdapter;
import com.example.madee.sga.Dialogs;
import com.example.madee.sga.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Event extends AppCompatActivity {

    Dialogs dialogs = new Dialogs();
    Button btnAddEvent;
    RecyclerView data;
    String JData = "";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https:sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private EventApi _eventApi;

    public Event() {
        _eventApi = retrofit.create(EventApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar bar = findViewById(R.id.EventBar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = findViewById(R.id.recyclerView);
        data.setLayoutManager(new LinearLayoutManager(this));

        btnAddEvent = findViewById(R.id.button);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Event.this, AddNewEvent.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dialogs.Loader(Event.this, 0);
        GetEvents();
    }

    public void GetEvents() {
        final Call<ResponseBody> event = _eventApi.GetAllEvents(1);
        event.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JData = response.body().string();
                    if (JData != "") {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        TypeToken<ArrayList<com.example.madee.sga.Models.Event>> token = new TypeToken<ArrayList<com.example.madee.sga.Models.Event>>() {
                        };
                        ArrayList<com.example.madee.sga.Models.Event> _eventList = gson.fromJson(JData, token.getType());
                        Dialogs.Loader(Event.this, 1);
                        data.setAdapter(new EventAdapter(Event.this, _eventList));
                    } else {
                        Dialogs.Loader(Event.this, 1);
                        Dialogs.ShowErrorDialog("Events Not Found", Event.this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(Event.this, 1);
                    Dialogs.ShowErrorDialog("Error, Fetching Events", Event.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(Event.this, 1);
                Dialogs.ShowErrorDialog("Error, Fetching Events", Event.this);
            }
        });
    }
}