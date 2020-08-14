package com.example.l12ws;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent i = getIntent();
        String method = i.getStringExtra("method");

        if(method.equalsIgnoreCase("listView")){
            String lat = i.getStringExtra("lat");
            String longt = i.getStringExtra("long");
            String type = i.getStringExtra("type");
            String msg = i.getStringExtra("msg");

            LatLng sg = new LatLng(Float.parseFloat(lat), Float.parseFloat(longt));
            mMap.addMarker(new MarkerOptions().position(sg).title(type).snippet(msg));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sg,12));
        }
        else if(method.equalsIgnoreCase("action")){
            ArrayList<Incident> incidents = (ArrayList<Incident>) i.getSerializableExtra("list");

            for(int x = 0; x < incidents.size(); x ++){
                String lat = incidents.get(x).getLatitude();
                String longt = incidents.get(x).getLongtitude();
                String type = incidents.get(x).getType();
                String msg = incidents.get(x).getMessage();

                LatLng sg = new LatLng(Float.parseFloat(lat), Float.parseFloat(longt));
                mMap.addMarker(new MarkerOptions().position(sg).title(type).snippet(msg));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sg,12));
            }

        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}