package com.example.madee.sga;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class GetGpsLocation implements LocationListener {
    //Variable declarations
    private final Context appContext;
    boolean isGpsEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location loc;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCCE_CHANGE_FOR_UPDATES_IN_METERS = 10;
    private static final long MINIMUM_TIME_BETWEEN_UPDATE_IN_MINUTES = 1000 * 30 * 1;


    protected LocationManager locationManager;

    public GetGpsLocation(Context appContext) {
        this.appContext = appContext;
    }

    @SuppressLint("MissingPermission")
    public Location getLoc() {
        try {
            locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGpsEnabled != true && isNetworkEnabled != true) {
                return null;
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATE_IN_MINUTES, MIN_DISTANCCE_CHANGE_FOR_UPDATES_IN_METERS, this);
                    Log.d("Network", "working");
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    }
                }
            }
            if (isGpsEnabled) {
                if (loc == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATE_IN_MINUTES, MIN_DISTANCCE_CHANGE_FOR_UPDATES_IN_METERS, this);
                    Log.d("GPS", "working");
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    }
                }
            }

        } catch (Exception ex) {

        }
        return loc;
    }

    public double getLatitude() {
        if (loc != null) {
            latitude = loc.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (loc != null) {
            longitude = loc.getLongitude();
        }
        return longitude;
    }

    public void ShowSettingIfLocationDisabled() {
        AlertDialog.Builder alert = new AlertDialog.Builder(appContext);
        alert.setTitle("Enable GPS");
        alert.setMessage("Please Enable GPS");
        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                appContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
