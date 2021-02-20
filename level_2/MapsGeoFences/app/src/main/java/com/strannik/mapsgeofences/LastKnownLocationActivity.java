package com.strannik.mapsgeofences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// https://developer.android.com/training/location/retrieve-current.html
// https://developer.android.com/training/location/change-location-settings.html

public class LastKnownLocationActivity extends AppCompatActivity {

    private TextView lon;
    private TextView lat;
    private Button last;

//    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastknownlocation);

        lon = (TextView) findViewById(R.id.text_longitude);
        lat = (TextView) findViewById(R.id.text_latitude);
        last = (Button) findViewById(R.id.last_location);


        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocation();
            }
        });
    }

    void requestLocation()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION}, 33);
            return;
        }
        /*
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                {
                    lat.setText(""+location.getLatitude());
                    lon.setText(""+location.getLongitude());
                }
            }
        });
        */

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 33)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                requestLocation();
        }
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}