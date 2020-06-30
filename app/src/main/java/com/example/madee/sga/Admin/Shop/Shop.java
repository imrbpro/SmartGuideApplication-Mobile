package com.example.madee.sga.Admin.Shop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.madee.sga.API.ShopApi;
import com.example.madee.sga.Admin.Shop.Adapters.ShopDataAdapter;
import com.example.madee.sga.MainActivity2;
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


public class Shop extends AppCompatActivity {
    private static final String TAG = MainActivity2.class.getName();
    //Declarations
    Dialog LoaderDialog;
    Button btnAddShop;
    RecyclerView data;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ShopApi _shopsApi;
    private String JData = "";

    public Shop() {
        _shopsApi = retrofit.create(ShopApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Toolbar bar = findViewById(R.id.ShopBar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnAddShop = findViewById(R.id.button);
        btnAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Shop.this, AddNew.class));
            }
        });
        data = findViewById(R.id.recyclerView);
        data.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Loader(0);
        GetShops();
    }

    public void GetShops() {
        Call<ResponseBody> _shops = _shopsApi.GetAllShops(1);
        _shops.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JData = response.body().string();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    TypeToken<ArrayList<com.example.madee.sga.Models.Shop>> token = new TypeToken<ArrayList<com.example.madee.sga.Models.Shop>>() {
                    };
                    ArrayList<com.example.madee.sga.Models.Shop> shoplist = gson.fromJson(JData, token.getType());
                    Loader(1);
                    data.setAdapter(new ShopDataAdapter(Shop.this, shoplist));
                } catch (IOException e) {
                    e.printStackTrace();
                    Loader(1);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    public void Loader(int flag) {
        switch (flag) {
            case 0:
                LoaderDialog = new Dialog(Shop.this);
                LoaderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LoaderDialog.setContentView(R.layout.wait_loader);
                LoaderDialog.show();
                break;
            case 1:
                LoaderDialog.dismiss();
                break;
            default:
                LoaderDialog.dismiss();
        }
    }
}
