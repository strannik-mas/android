package com.strannik.mapsgeofences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

// https://developer.android.com/training/location/change-location-settings.html
// https://developer.android.com/training/location/receive-location-updates.html

public class ShowLatLngActivitySimple extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final int REQUEST_FINE_LOCATION = 87;
    private static final int REQUEST_CHECK_SETTINGS = 99;
    private GoogleApiClient googleApiClient;

    private TextView textLatitude;
    private TextView textLongitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        textLatitude = findViewById(R.id.text_latitude);
        textLongitude = findViewById(R.id.text_longitude);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Сервисная функция чтобы создать запрос к системе навигации
    protected LocationRequest createLocationRequest() {
        @SuppressLint("RestrictedApi") LocationRequest mLocationRequest = new LocationRequest();
        // Интервал между получениями изменений координат
        mLocationRequest.setInterval(10000);
        // Минимальный интервал между получениями изменений координат
        mLocationRequest.setFastestInterval(5000);
        // Спутникровая навигация
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // PRIORITY_BALANCED_POWER_ACCURACY - WiFi/Cellular
        // PRIORITY_HIGH_ACCURACY - GPS
        // PRIORITY_LOW_POWER - Cellular ?
        // PRIORITY_NO_POWER
        return mLocationRequest;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // При подключении к GoogleApiClient, проверим, пожем ли мы послать
        // нужный запрос системе навигации
        checkRequest();
        if(googleApiClient.isConnected())
            requestLocationUpdates();

    }

    // Прежде чем запросить обновления навигационных данных, нужно проверить,
    // есть ли нужные права
    private void requestLocationUpdates() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
        {
            // Прав нет, запросим их у пользователя
            requestPermissions(
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    } ,
                    REQUEST_FINE_LOCATION
            );
        }
        else
        {
            // Права есть, запросим обновления координат у
            // системы навигации
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient,
                    createLocationRequest(),
                    this
            );
        }
    }

    // Подключаемся к GoogleApiClient
    @Override
    protected void onResume() {
        super.onResume();
        if(!googleApiClient.isConnected() || !googleApiClient.isConnecting())
        {
            googleApiClient.connect();
        }
    }

    // Отключаемся к GoogleApiClient
    @Override
    protected void onPause() {
        super.onPause();
        if(googleApiClient.isConnected() || googleApiClient.isConnecting())
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    // Реакция пользователя
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_FINE_LOCATION
                && permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            // Проверим, можем ли мы послать системе навигации
            // нужный нам запрос
            checkRequest();
        }
    }

    private void checkRequest() {
        if(googleApiClient.isConnected())
            requestLocationUpdates();
    }

    /*
    private void checkRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        // Если можем послать запрос
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                if(googleApiClient.isConnected())
                    requestLocationUpdates();
            }
        });

        // Не можем послать запрос
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            // Покажем диалог с просьбой включить навигацию
                            resolvable.startResolutionForResult(ShowLatLngActivitySimple.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });

    }
    */

    // Сюда приходит ответ пользователя на диалог
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CHECK_SETTINGS)
        {
            if(resultCode == RESULT_OK)
            {
                if(googleApiClient.isConnected())
                    requestLocationUpdates();
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
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
    public void onLocationChanged(Location location) {

        textLatitude.setText(""+location.getLatitude());
        textLongitude.setText(""+location.getLongitude());
    }
}