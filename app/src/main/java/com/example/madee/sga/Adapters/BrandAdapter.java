package com.example.madee.sga.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madee.sga.API.BrandApi;
import com.example.madee.sga.Admin.Brand.UpdateBrand;
import com.example.madee.sga.Dialogs;
import com.example.madee.sga.Models.Brand;
import com.example.madee.sga.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    public ArrayList<Brand> Data;
    public BrandApi _brandApi;
    Context context;
    String Response = "";
    Dialogs dialogs = new Dialogs();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public BrandAdapter(Context context, ArrayList<Brand> data) {
        Data = data;
        this.context = context;
        _brandApi = retrofit.create(BrandApi.class);
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.brandlist, viewGroup, false);
        return new BrandViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final BrandViewHolder brandViewHolder, int i) {
        final Brand _brand = Data.get(i);
        String url = "";
        final int brandid = _brand.getBrand_id();
        brandViewHolder.txtBrandName.setText(_brand.getBrand_name());
        brandViewHolder.txtOwnerName.setText(_brand.getBrand_owner());
        brandViewHolder.txtShopNumbers.setText(_brand.getShop_no());
        url = _brand.getBrand_logo();
        RequestOptions options = new RequestOptions();
        options.circleCrop()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder);
        Glide.with(brandViewHolder.brandimg.getContext()).load(url).apply(options).into(brandViewHolder.brandimg);
        brandViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateBrand.class);
                intent.putExtra("BrandId", brandid);
                view.getContext().startActivity(intent);
            }
        });
        brandViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            int position = 0;

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm Delete?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteBrand(brandid);
                                position = brandViewHolder.getLayoutPosition();
                                Data.remove(position);
                                notifyItemRemoved(position);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public void DeleteBrand(int id) {
        Call<ResponseBody> deletebrand = _brandApi.DeleteBrand(id);
        deletebrand.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body().string().equals("200")) {
                        Dialogs.ShowSuccessDialog("Brand Deleted Successfully", context);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        TextView txtShopNumbers, txtBrandName, txtOwnerName;
        ImageView brandimg;
        ImageButton btnUpdate, btnDelete;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBrandName = itemView.findViewById(R.id.listbrandName);
            txtOwnerName = itemView.findViewById(R.id.listbrandowner);
            txtShopNumbers = itemView.findViewById(R.id.listbrandshopno);
            brandimg = itemView.findViewById(R.id.listbrandimage);
            btnUpdate = itemView.findViewById(R.id.listbrandbtnupdate);
            btnDelete = itemView.findViewById(R.id.listbrandbtndelete);
        }
    }
}
