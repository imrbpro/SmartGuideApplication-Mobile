package com.example.madee.sga.Admin.Brand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.madee.sga.API.BrandApi;
import com.example.madee.sga.Dialogs;
import com.example.madee.sga.Models.Brand;
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

public class UpdateBrand extends AppCompatActivity {

    TextView txtName, txtOwner, txtShopno, txtImagepath;
    Button btnUpdate;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    BrandApi _brandApi;

    int BrandId = 0;
    String Response = "";

    Dialogs dialogs = new Dialogs();

    public UpdateBrand() {
        _brandApi = retrofit.create(BrandApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_brand);

        Intent intent = getIntent();
        BrandId = intent.getIntExtra("BrandId", 0);

        txtName = findViewById(R.id.txtaBrandName);
        txtOwner = findViewById(R.id.txtOwnerName);
        txtShopno = findViewById(R.id.txtShopComaSeperated);
        txtImagepath = findViewById(R.id.txtBrandImage);

        Dialogs.Loader(this, 0);
        GetBrandById(BrandId);

        btnUpdate = findViewById(R.id.btnUpdateBrand);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.Loader(UpdateBrand.this, 0);
                JSONObject obj = new JSONObject();
                try {
                    obj.put("Id", BrandId);
                    obj.put("Name", txtName.getText());
                    obj.put("ShopNumbers", txtShopno.getText());
                    obj.put("ownername", txtOwner.getText());
                    obj.put("logopath", txtImagepath.getText());
                    UpdateBrand(obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void UpdateView(String name, String owner, String shopno, String imagepath) {
        txtName.setText(name);
        txtOwner.setText(owner);
        txtShopno.setText(shopno);
        txtImagepath.setText(imagepath);
    }

    public void GetBrandById(int id) {
        Call<ResponseBody> getbrand = _brandApi.GetBrandById(id);
        getbrand.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Response = response.body().string();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Brand[] brand = gson.fromJson(Response, Brand[].class);
                    UpdateView(brand[0].brand_name, brand[0].brand_owner, brand[0].shop_no, brand[0].brand_logo);
                    Dialogs.Loader(UpdateBrand.this, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(UpdateBrand.this, 1);
                    Dialogs.ShowErrorDialog("Error Occured..!", UpdateBrand.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(UpdateBrand.this, 1);
                Dialogs.ShowErrorDialog("Error Occured..!", UpdateBrand.this);
            }
        });
    }

    public void UpdateBrand(String brand) {
        Call<ResponseBody> update = _brandApi.UpdateBrand(brand);
        update.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body().string() == "200")
                        Dialogs.Loader(UpdateBrand.this, 1);
                    Dialogs.ShowSuccessDialog("Brand Updated Successfully", UpdateBrand.this);
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(UpdateBrand.this, 1);
                    Dialogs.ShowErrorDialog("Error: Brand Not Updated", UpdateBrand.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(UpdateBrand.this, 1);
                Dialogs.ShowErrorDialog("Error: Brand Not Updated", UpdateBrand.this);
            }
        });
    }
}