package com.example.madee.sga.Admin.Brand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.madee.sga.API.BrandApi;
import com.example.madee.sga.Adapters.BrandAdapter;
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

public class Brand extends AppCompatActivity {

    Dialogs dialogs = new Dialogs();
    Button btnAddBrand;
    RecyclerView data;
    String JData = "";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https:sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private BrandApi _brandApi;

    public Brand() {
        _brandApi = retrofit.create(BrandApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        Toolbar bar = findViewById(R.id.BrandBar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = findViewById(R.id.recyclerView);
        data.setLayoutManager(new LinearLayoutManager(this));

        btnAddBrand = findViewById(R.id.button);
        btnAddBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Brand.this, AddNewBrand.class);
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
        Dialogs.Loader(Brand.this, 0);
        GetBrands();
    }

    public void GetBrands() {
        final Call<ResponseBody> brand = _brandApi.GetAllBrands(1);
        brand.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JData = response.body().string();
                    if (JData != "") {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        TypeToken<ArrayList<com.example.madee.sga.Models.Brand>> token = new TypeToken<ArrayList<com.example.madee.sga.Models.Brand>>() {
                        };
                        ArrayList<com.example.madee.sga.Models.Brand> _brandList = gson.fromJson(JData, token.getType());
                        Dialogs.Loader(Brand.this, 1);
                        data.setAdapter(new BrandAdapter(Brand.this, _brandList));
                    } else {
                        Dialogs.Loader(Brand.this, 1);
                        Dialogs.ShowErrorDialog("Brands Not Found", Brand.this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(Brand.this, 1);
                    Dialogs.ShowErrorDialog("Error, Fetching Brands", Brand.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(Brand.this, 1);
                Dialogs.ShowErrorDialog("Error, Fetching Brands", Brand.this);
            }
        });
    }
}