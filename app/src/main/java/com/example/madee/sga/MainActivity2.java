package com.example.madee.sga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.madee.sga.API.ShopApi;
import com.example.madee.sga.Models.Shop;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;


public class MainActivity2 extends AppCompatActivity {
    //Declarations
    private Button btn, btn1;
    private static final String TAG = MainActivity2.class.getName();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ShopApi _shopsApi;
    //constructor
    public MainActivity2(){
        //api classes must be initialized in constructors
         _shopsApi = retrofit.create(ShopApi.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final ImageView imageView = (ImageView) findViewById(R.id.J);
        final ImageView imageView1 = (ImageView) findViewById(R.id.j1);
        final ImageView imageView2 = (ImageView) findViewById(R.id.j2);
        final ImageView imageView3 = (ImageView) findViewById(R.id.j3);
        final ImageView imageView4 = (ImageView) findViewById(R.id.j4);
        final ImageView imageView5 = (ImageView) findViewById(R.id.j5);
        final ImageView imageView6 = (ImageView) findViewById(R.id.j6);
        final ImageView imageView7 = (ImageView) findViewById(R.id.j7);
        final ImageView imageView8 = (ImageView) findViewById(R.id.j8);
        final ImageView imageView9 = (ImageView) findViewById(R.id.j9);
        final ImageView imageView10 = (ImageView) findViewById(R.id.j10);
        final ImageView imageView11 = (ImageView) findViewById(R.id.j11);
        final ImageView imageView12 = (ImageView) findViewById(R.id.j12);

        imageView.setVisibility(View.INVISIBLE);


        //Location loc;
        //GetGpsLocation gpsLocation = new GetGpsLocation(this);
        //loc = gpsLocation.getLoc();
        //loc.getLatitude();///waghera waghera

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView1));
        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView2));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView3));
        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView4));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView5));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView6));
        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView7));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView8));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView9));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView10));

        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView11));
        Glide.with(this)
                .load(R.raw.loc3)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new DrawableImageViewTarget(imageView12));


        //Getting API
        //GetShops();

        //Post Api For adding new Shop
        /*try{
            JSONObject obj = new JSONObject();
            obj.put("ShopName","StarBuck Added");
            obj.put("OwnerName","StarBuck Shop");
            obj.put("Longitude","0.0000");
            obj.put("Latitude","0.0000");
            obj.put("Imagepath","path");
            Log.d(TAG, obj.toString());
            PostShop(obj.toString());
        }
        catch (Exception ex){

        }*/


        //Update Api For updating shops with respective ids
        /*try{
            JSONObject obj = new JSONObject();
            obj.put("ShopId",9);
            obj.put("ShopName","StarBuck");
            obj.put("OwnerName","StarBuck");
            obj.put("Longitude","0.0000");
            obj.put("Latitude","0.0000");
            obj.put("Imagepath","path");
            Log.d(TAG, obj.toString());
            UpdateShop(obj.toString());
        }
        catch (Exception ex){

        }*/

        //Delete Api
        //RemoveShop(10);
    }
    public void GetShops(){
            Call<List<Shop>> _shops = _shopsApi.GetAllShops(1);

            _shops.enqueue(new Callback<List<Shop>>() {
                @Override
                public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                    Log.d(TAG, "onResponse: ");
                    Toast.makeText(MainActivity2.this,"Response"+response.body(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<List<Shop>> call, Throwable t) {

                    Toast.makeText(MainActivity2.this,"Fail",Toast.LENGTH_LONG).show();
                }
            });
    }
    public void PostShop(String shop){

        Call<ResponseBody> AddShop = _shopsApi.AddShop(shop);

        AddShop.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try{
                String StatusCode = response.body().string();
                if( StatusCode.equals("201")) {
                    Toast.makeText(MainActivity2.this, "Shop Created", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity2.this, "Shop Not Created", Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){}
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity2.this,"Failded to add new shop",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateShop(String shop){

        Call<ResponseBody> UpdateShop = _shopsApi.UpdateShop(shop);
        UpdateShop.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    String StatusCode = response.body().string();
                    if( StatusCode.equals("200")) {
                        Toast.makeText(MainActivity2.this, "Shop Updated", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity2.this, "Shop Not Updated", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){}

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity2.this,"Failed to Update shop",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void RemoveShop(int id){
        Call<ResponseBody> DeleteShop =  _shopsApi.RemoveShop(id);
        DeleteShop.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity2.this, response.raw().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "Error",Toast.LENGTH_LONG).show();
            }
        });
    }
}
