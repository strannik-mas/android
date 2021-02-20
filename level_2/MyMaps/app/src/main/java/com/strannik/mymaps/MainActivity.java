package com.strannik.mymaps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private static LatLng moscow = new LatLng(55.7559, 37.6173);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.addCircle(
                        new CircleOptions()
                        .center(latLng)
                        .radius(200_000)
                        .fillColor(0x440000aa)
                        .strokeColor(0x440000aa)
                );
            }
        });
    }

    public void findMoscow(View view) {
        if(map != null) {
            map.addMarker(
                    new MarkerOptions()
                    .position(moscow)
                    .title("Moscow")
                    .snippet("Population: 11.92 millions")
            );
            map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(moscow, 15)
            );
        }
    }
}