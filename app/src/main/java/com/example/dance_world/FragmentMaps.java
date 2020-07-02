package com.example.dance_world;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

        //Initialize fused location
        client = LocationServices.getFusedLocationProviderClient(supportMapFragment.getContext());

        //Check permission
        if(ActivityCompat.checkSelfPermission(supportMapFragment.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted, call method
            setFestivalLocations();
        } else {
            //When permission denied
            //Request permission
            ActivityCompat.requestPermissions(supportMapFragment.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void setFestivalLocations() {
        //Initialize task Location
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                //When success
                if(location != null) {
                    //Sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            for (final Festival festival: festivals) {
                                LatLng latLng = new LatLng(festival.gps_latitude, festival.gps_longitude);
                                MarkerOptions options = new MarkerOptions().position(latLng)
                                        .title(festival.city);

                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        Intent intent = new Intent(getContext(), DetailActivity.class);
                                        intent.putExtra("festivalName", festival.name);
                                        startActivity(intent);
                                        return false;
                                    }
                                });

                                //Zoom map
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                                //Add marker on map
                                googleMap.addMarker(options);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    public boolean onMarkerClick(final Marker marker) {
        return false;
    }
}
