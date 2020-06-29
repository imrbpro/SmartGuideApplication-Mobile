package com.example.madee.sga.Admin.Shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madee.sga.API.ShopApi;
import com.example.madee.sga.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpdateShop extends AppCompatActivity {

    private static final String TAG = UpdateShop.class.getName();
    int ShopId = 0;
    String JData = "";
    private EditText txtShopName, txtShopOwner, txtShopLatitude, txtShopLongitude, txtShopImagePath;
    private Button btnUpdate;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ShopApi _shopsApi;

    public UpdateShop() {
        _shopsApi = retrofit.create(ShopApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shop);

        Intent intent = getIntent();
        ShopId = intent.getIntExtra("ShopId", 0);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtShopName = findViewById(R.id.txtShopName);
        txtShopOwner = findViewById(R.id.txtOwnerName);
        txtShopLatitude = findViewById(R.id.txtLatitude);
        txtShopLongitude = findViewById(R.id.txtLongitude);
        txtShopImagePath = findViewById(R.id.imagepath);
        btnUpdate = findViewById(R.id.btnUpdateShop);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("ShopId", ShopId);
                    obj.put("ShopName", txtShopName.getText());
                    obj.put("OwnerName", txtShopOwner.getText());
                    obj.put("Longitude", txtShopLongitude.getText());
                    obj.put("Latitude", txtShopLatitude.getText());
                    obj.put("Imagepath", txtShopImagePath.getText());
                    Log.d(TAG, obj.toString());
                    UpdateShop(obj.toString());
                } catch (Exception ex) {

                }
            }
        });
        GetShopDataById(ShopId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void UpdateView(String Shopname, String ShopOwner, double Latitude, double Longitude, String Imagepath) {
        txtShopName.setText(Shopname);
        txtShopOwner.setText(ShopOwner);
        txtShopLatitude.setText("" + Latitude);
        txtShopLongitude.setText("" + Longitude);
        txtShopImagePath.setText(Imagepath);
    }

    public void GetShopDataById(int id) {
        Call<ResponseBody> _shops = _shopsApi.GetShopById(id);
        _shops.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JData = response.body().string();
                    Log.d(TAG, "onResponse: " + JData);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    com.example.madee.sga.Models.Shop[] shopjson = gson.fromJson(JData, com.example.madee.sga.Models.Shop[].class);
                    UpdateView(shopjson[0].shop_name, shopjson[0].shop_owner, shopjson[0].shop_latitude, shopjson[0].shop_longitude, shopjson[0].shop_image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void UpdateShop(String shop) {

        Call<ResponseBody> UpdateShop = _shopsApi.UpdateShop(shop);
        UpdateShop.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String StatusCode = response.body().string();
                    if (StatusCode.equals("200")) {
                        Toast.makeText(UpdateShop.this, "Shop Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UpdateShop.this, "Shop Not Updated", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UpdateShop.this, "Failed to Update shop", Toast.LENGTH_LONG).show();
            }
        });
    }
}