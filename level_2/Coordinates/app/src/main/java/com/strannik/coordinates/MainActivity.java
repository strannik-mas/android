package com.strannik.coordinates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TextView lat;
    private TextView lon;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //не соеденились ли мы уже и если соеденились, получим данные
        if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnecting() || googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient,
                    this
            );
            googleApiClient.disconnect();
        }
    }

    /**
     * взаимодействие с googleApiClient (подсоединились к нему, он полностью проинициализирован)
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkRequest();
    }

    private void checkRequest() {
        if (googleApiClient.isConnected()){
            requestLocationUppdates();
        }
    }

    private void requestLocationUppdates() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //диалог о получении разрешения
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    555
            );
        } else {
            //ACCESS_FINE_LOCATION уже есть
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient,
                    getLocationRequest(),
                    this
            );
        }
    }

    private LocationRequest getLocationRequest() {
        @SuppressLint("RestrictedApi") LocationRequest request = new LocationRequest();
        request.setInterval(10_000);
        request.setFastestInterval(5_000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);    //GPS
        //LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY      //Wi-Fi+Cell
        //LocationRequest.PRIORITY_NO_POWER   //не тратить энергию на получение (последнее либо из какого-то приложения)
        //LocationRequest.PRIORITY_LOW_POWER   //Cell
        return request;
    }

    /**
     * соединение с googleApiClient было заморожено
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * соединение не удалось (не смогли подсоединиться к googleApiClient)
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * выполняется, когда приходят  новые географические координаты
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        lat.setText("" + location.getLatitude());
        lon.setText("" + location.getLongitude());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //пришел ли ответ на наш запрос
        if (requestCode == 555) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkRequest();
            } else {
                //если пользователь не дал прав
                Toast.makeText(this, "не могу работать", Toast.LENGTH_SHORT).show();
            }
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}