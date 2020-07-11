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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.madee.sga.API.ShopApi;
import com.example.madee.sga.Admin.Shop.UpdateShop;
import com.example.madee.sga.Models.Shop;
import com.example.madee.sga.R;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ShopDataAdapter extends RecyclerView.Adapter<ShopDataAdapter.ShopViewHolder> {

    public ArrayList<Shop> Data;
    Context context;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ShopApi _shopsApi;

    public ShopDataAdapter(Context context, ArrayList<Shop> data) {
        this.context = context;
        this.Data = data;
        _shopsApi = retrofit.create(ShopApi.class);
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.listdatalayout, viewGroup, false);
        return new ShopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopViewHolder shopViewHolder, int i) {
        final Shop shop = Data.get(i);
        String url = "";
        shopViewHolder.txtTitle.setText(shop.shop_name);
        shopViewHolder.txtowner.setText(shop.shop_owner);
        shopViewHolder.btnDelete.setId(shop.shop_id);
        shopViewHolder.btnUpdate.setId(shop.shop_id);
        url = shop.shop_image;
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder);
        Glide.with(shopViewHolder.img.getContext())
                .load(url)
                .apply(options)
                .into(shopViewHolder.img);

        shopViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateShop.class);
                intent.putExtra("ShopId", shopViewHolder.btnUpdate.getId());
                view.getContext().startActivity(intent);
            }
        });

        shopViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            int position = 0;

            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm Delete?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        RemoveShop(shopViewHolder.btnDelete.getId(), view.getContext());
                                        position = shopViewHolder.getLayoutPosition();
                                        Data.remove(position);
                                        shopViewHolder.getLayoutPosition();
                                        notifyItemRemoved(position);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public void RemoveShop(int id, final Context ctx) {
        Call<ResponseBody> DeleteShop = _shopsApi.RemoveShop(id);
        DeleteShop.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ctx, response.raw().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageButton btnUpdate, btnDelete;
        TextView txtTitle, txtowner;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.dataimage);
            btnUpdate = itemView.findViewById(R.id.databtnupdate);
            btnDelete = itemView.findViewById(R.id.databtndelete);
            txtTitle = itemView.findViewById(R.id.datatxt);
            txtowner = itemView.findViewById(R.id.datatxtowner);
        }
    }
}
