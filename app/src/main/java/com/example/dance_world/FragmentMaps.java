package com.example.dance_world;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Festival;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class FragmentMaps extends Fragment implements OnMapReadyCallback {
    //Initialize variable
    private GoogleMap map;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    private DatabaseHelper helper;
    List<Festival> festivals;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = DatabaseHelper.getInstance(getContext());
        festivals = helper.FestivalDao().getAll();

        //Assign variable
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.gmap);

        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for (Festival festival: festivals) {
            LatLng ll = new LatLng(festival.gps_latitude, festival.gps_longitude);
            map.addMarker(new MarkerOptions().position(ll).title(festival.city));
            map.moveCamera(CameraUpdateFactory.newLatLng(ll));
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String markerTitile= marker.getTitle();
                Log.w("aaaaa", markerTitile);
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("festivalCity", markerTitile);
                startActivity(intent);

                return  false;
            }
        });

    }
}
