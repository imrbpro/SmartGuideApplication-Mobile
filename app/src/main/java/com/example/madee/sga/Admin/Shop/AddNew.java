package com.example.madee.sga.Admin.Shop;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madee.sga.API.ShopApi;
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
    private String TAG = "Add New";
    private Dialog SuccessDialog, FailedDialog, LoaderDialog;
    private TextView txt, txtfailed;
    private EditText txtShopName, txtOwnerName, txtLatitude, txtLongitude, txtImagePath;
    private Button btnAddShop, dbtn, dbtn1;

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
                    Loader(0);
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
                        Loader(1);
                        ShowSuccessDialog();
                    } else {
                        Loader(1);
                        ShowErrorDialog();
                    }
                } catch (Exception ex) {
                    Loader(1);
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddNew.this, "Failed to add new shop", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ShowSuccessDialog() {
        SuccessDialog = new Dialog(AddNew.this);
        SuccessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SuccessDialog.setContentView(R.layout.dialog_created);
        SuccessDialog.setTitle("Shop Title");
        txt = SuccessDialog.findViewById(R.id.created_dialog_body);
        txt.setText("Shop Created Successfully");
        dbtn = SuccessDialog.findViewById(R.id.created_dialog_btn);
        dbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuccessDialog.dismiss();
            }
        });
        SuccessDialog.show();
    }

    public void ShowErrorDialog() {
        FailedDialog = new Dialog(AddNew.this);
        FailedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        FailedDialog.setContentView(R.layout.dialog_failed);
        txtfailed = FailedDialog.findViewById(R.id.failed_dialog_body);
        txtfailed.setText("Shop Not Created");
        dbtn1 = findViewById(R.id.failed_dialog_btn);
        dbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FailedDialog.dismiss();
            }
        });
    }

    public void Loader(int flag) {
        switch (flag) {
            case 0:
                LoaderDialog = new Dialog(AddNew.this);
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
