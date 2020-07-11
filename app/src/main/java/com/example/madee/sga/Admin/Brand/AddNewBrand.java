package com.example.madee.sga.Admin.Brand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.madee.sga.API.BrandApi;
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

public class AddNewBrand extends AppCompatActivity {
    public BrandApi _brandApi;
    Toolbar bar;
    EditText txtbrandname, txtbrandowner, txtshopscomasperated, txtbrandlogo;
    Button btnAddBrand;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    String StatusCode = "";
    Dialogs dialogs = new Dialogs();

    public AddNewBrand() {
        _brandApi = retrofit.create(BrandApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_brand);
        bar = findViewById(R.id.toolbar2);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtbrandname = findViewById(R.id.txtaBrandName);
        txtbrandowner = findViewById(R.id.txtOwnerName);
        txtshopscomasperated = findViewById(R.id.txtShopNumbers);
        txtbrandlogo = findViewById(R.id.txtImagepath);
        btnAddBrand = findViewById(R.id.btnAddBrand);
        btnAddBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("Name", txtbrandname.getText());
                    obj.put("ShopNumbers", txtshopscomasperated.getText());
                    obj.put("ownername", txtbrandowner.getText());
                    obj.put("logopath", txtbrandlogo.getText());
                    Dialogs.Loader(AddNewBrand.this, 0);
                    AddBrand(obj.toString());
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

    public void AddBrand(String brand) {
        Call<ResponseBody> addbrand = _brandApi.AddBrand(brand);
        addbrand.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    StatusCode = response.body().string();
                    if (StatusCode.equals("201")) {
                        Dialogs.Loader(AddNewBrand.this, 1);
                        Dialogs.ShowSuccessDialog("Brand Added..!", AddNewBrand.this);
                    } else {
                        Dialogs.Loader(AddNewBrand.this, 1);
                        Dialogs.ShowErrorDialog("Brand Not Added..!", AddNewBrand.this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Dialogs.Loader(AddNewBrand.this, 1);
                    Dialogs.ShowErrorDialog("Exception Occured while adding brand ", AddNewBrand.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dialogs.Loader(AddNewBrand.this, 1);
                Dialogs.ShowErrorDialog("Brand Creation Failed", AddNewBrand.this);
            }
        });
    }
}
