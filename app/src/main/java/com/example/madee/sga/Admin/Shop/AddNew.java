package com.example.madee.sga.Admin.Shop;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madee.sga.API.ShopApi;
import com.example.madee.sga.Dialogs;
import com.example.madee.sga.R;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddNew extends AppCompatActivity {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ShopApi _shopsApi;
    Dialogs dialogs = new Dialogs();
    private EditText txtShopName, txtOwnerName, txtLatitude, txtLongitude, txtImagePath;
    private String TAG = "Add New", Error = "Error : Shop Not Created", Success = "Shop Created Successfully";
    private Button btnAddShop;

    public AddNew() {
        _shopsApi = retrofit.create(ShopApi.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shop);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtShopName = findViewById(R.id.txtShopName);
        txtOwnerName = findViewById(R.id.txtOwnerName);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        txtImagePath = findViewById(R.id.imagepath);
        btnAddShop = findViewById(R.id.btnAddShop);
        btnAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    JSONObject obj = new JSONObject();
                    obj.put("ShopName", txtShopName.getText());
                    obj.put("OwnerName", txtOwnerName.getText());
                    obj.put("Longitude", txtLatitude.getText());
                    obj.put("Latitude", txtLongitude.getText());
                    obj.put("Imagepath", txtImagePath.getText());
                    Log.d(TAG, obj.toString());
                    dialogs.Loader(AddNew.this, 0);
                    AddNewShop(obj.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void AddNewShop(String shop) {

        Call<ResponseBody> AddShop = _shopsApi.AddShop(shop);

        AddShop.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String StatusCode = response.body().string();
                    if (StatusCode.equals("201")) {
                        dialogs.Loader(AddNew.this, 1);
                        dialogs.ShowSuccessDialog(Success, AddNew.this);
                    } else {
                        dialogs.Loader(AddNew.this, 1);
                        dialogs.ShowErrorDialog(Error, AddNew.this);
                    }
                } catch (Exception ex) {
                    dialogs.Loader(AddNew.this, 1);
                    dialogs.ShowErrorDialog(Error, AddNew.this);
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddNew.this, "Failed to add new shop", Toast.LENGTH_LONG).show();
                dialogs.Loader(AddNew.this, 1);
                dialogs.ShowErrorDialog(Error, AddNew.this);
            }
        });
    }

}
