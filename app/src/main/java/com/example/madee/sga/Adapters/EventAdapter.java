package com.example.madee.sga.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madee.sga.API.EventApi;
import com.example.madee.sga.Admin.Event.UpdateEvent;
import com.example.madee.sga.Models.Event;
import com.example.madee.sga.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public ArrayList<Event> Data;
    Context context;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sga.somee.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private EventApi _EventsApi;

    public EventAdapter(Context context, ArrayList<Event> data) {
        this.context = context;
        this.Data = data;
        _EventsApi = retrofit.create(EventApi.class);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.eventlist, viewGroup, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, int i) {

        final Event _event = Data.get(i);
        //String date = GetFormatedDate(_event.DateTime);

        eventViewHolder.txtDate.setText(_event.event_datetime);
        eventViewHolder.txtShopId.setText(String.valueOf(_event.shop_id));
        eventViewHolder.txtBrandId.setText(String.valueOf(_event.brand_id));
        eventViewHolder.txtDescription.setText(_event.event_details);
        eventViewHolder.btnDelete.setId(_event.event_id);
        eventViewHolder.btnUpdate.setId(_event.event_id);

        eventViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateEvent.class);
                intent.putExtra("EventId", eventViewHolder.btnUpdate.getId());
                view.getContext().startActivity(intent);
            }
        });

        eventViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            int position = 0;

            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm Delete?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        RemoveEvent(eventViewHolder.btnDelete.getId(), view.getContext());
                                        position = eventViewHolder.getLayoutPosition();
                                        Data.remove(position);
                                        eventViewHolder.getLayoutPosition();
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

    public void RemoveEvent(int id, final Context ctx) {
        Call<ResponseBody> DeleteEvent = _EventsApi.DeleteEvent(id);
        DeleteEvent.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ctx, "Event Deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public String GetFormatedDate(String Date) {
        java.util.Date _date;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            _date = df.parse(Date);
        } catch (ParseException | java.text.ParseException e) {
            throw new RuntimeException("Failed to parse date: ", e);
        }
        return String.valueOf(_date.getTime());
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnUpdate, btnDelete;
        TextView txtEventName, txtShopId, txtBrandId, txtDescription, txtDate;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            btnUpdate = itemView.findViewById(R.id.listEventbtnupdate);
            btnDelete = itemView.findViewById(R.id.listEventbtndelete);
            txtEventName = itemView.findViewById(R.id.listEventName);
            txtShopId = itemView.findViewById(R.id.listEventShopId);
            txtBrandId = itemView.findViewById(R.id.listEventBrandId);
            txtDescription = itemView.findViewById(R.id.listEventDescription);
            txtDate = itemView.findViewById(R.id.listEventDate);
        }
    }
}
