package com.strannik.mapsgeofences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

// https://developer.android.com/training/location/geofencing.html

public class GeoFenceActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<Status>
{
    private LatLng latLng;

    private static float radius = 200;
    private static long ONE_MINUTE = 60000;

    private static int colorRed  = 0x3300ff00;
    private static int colorBlue = 0x330000ff;

    private static int mMapType = GoogleMap.MAP_TYPE_NORMAL;
    private static final int REQUEST_FINE_LOCATION = 87;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onResume() {
        super.onResume();
        if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                addFence(latLng, radius);
            }
        });
    }

    private void addFence(LatLng latLng, float radius) {
        if (googleApiClient.isConnected()) {

            if (ActivityCompat.checkSelfPermission(GeoFenceActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            int locationMode = getLocationMode(GeoFenceActivity.this);
            if (locationMode != Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else {
                this.latLng = latLng;
                LocationServices.GeofencingApi.addGeofences(
                        googleApiClient,
                        getGeofencingRequest(this.latLng, radius),
                        getGeofencePendingIntent()
                ).setResultCallback(GeoFenceActivity.this);
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    @SuppressLint("RestrictedApi")
    void requestLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    REQUEST_FINE_LOCATION
            );
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient,
                    new LocationRequest()
                            .setInterval(3000)
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                    this
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_FINE_LOCATION)
        {
            if(permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                requestLocationUpdates();
            }
        }
    }

    public int getLocationMode(GeoFenceActivity context) {
        /*
            0 = LOCATION_MODE_OFF
            1 = LOCATION_MODE_SENSORS_ONLY
            2 = LOCATION_MODE_BATTERY_SAVING
            3 = LOCATION_MODE_HIGH_ACCURACY
        */
        int result = -1;
        try {
            result = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) { }
        return result;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("happy", "onLocationChanged " + location.toString());

        if (mMap != null) {

            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());

            //CameraUpdateFactory.newLatLng(point)

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

            mMap.addCircle(
                    new CircleOptions()
                            .radius(5)
                            .center(
                                    point
                            )
                            .fillColor(colorRed)
                            .strokeColor(colorRed)
            );
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("happy", "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("happy", "Connection failed");
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            if(mMap != null)
            {
                mMap.addCircle(
                        new CircleOptions()
                                .center(this.latLng)
                                .radius(radius)
                                .fillColor(colorBlue)
                                .strokeColor(colorBlue)
                );
            }
            Log.d("happy", "Geofence added");
        } else {
            Log.d("happy", "Trouble adding a geofence: " + status.toString());
        }
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, GeoFenceReceiver.class);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest(LatLng location, float radius) {

        Geofence fence = new Geofence.Builder()
                .setRequestId(String.valueOf(System.currentTimeMillis()))
                .setCircularRegion(
                        location.latitude,
                        location.longitude,
                        radius
                )
                .setExpirationDuration(ONE_MINUTE)
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(fence);
        return builder.build();
    }

}