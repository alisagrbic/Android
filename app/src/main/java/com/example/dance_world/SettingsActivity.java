package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dance_world.colorpicker.ColorPicker;
import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private DatabaseHelper helper;
    Spinner theme_spinner, perimeter_spinner, dance_spinner;
    Button Apply;
    private DrawerLayout drawer;
    ImageButton settings, liness, imageHeart;
    Toolbar toolbar;
    String col;
    LinearLayout navcolor;
    LocationManager locationManager;
    private GoogleMap map;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    List<Festival> festivals;
    List<String> namesOfFestivals = new ArrayList<>();
    String strings[] = {};
    Bundle b = new Bundle();
    String selectedDance;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        helper = DatabaseHelper.getInstance(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        theme_spinner = findViewById(R.id.theme_spinner);
        perimeter_spinner = findViewById(R.id.perimeter_spinner);
        dance_spinner = findViewById(R.id.dance_spinner);
        Apply = findViewById(R.id.Apply);
        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        imageHeart = findViewById(R.id.heart);
        toolbar = findViewById(R.id.toolbar);
        // navcolor = findViewById(R.id.navcolor);

        String color = getIntent().getStringExtra("colorTheme");
        ColorDrawable c = new ColorDrawable(Color.parseColor(color));

        toolbar.setBackground(c);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //customize header view
        final View header = navigationView.inflateHeaderView(R.layout.nav_header);
        header.setBackground(c);

        drawer = findViewById(R.id.drawerr_layout);


        String[] theme = new String[]{"App theme", "Color picker"};
        String[] perimeter = new String[]{"Perimeter","500km", "1000km", "2000km", "5000km", "All"};
        String[] dance = new String[]{"Dance", "Banchata", "Kizomba", "Salsa"};

        ArrayAdapter<String> adapterTheme = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, theme);
        theme_spinner.setAdapter(adapterTheme);

        theme_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Color picker")) {
                    // do your stuff
                    ColorPicker colorPicker = new ColorPicker(SettingsActivity.this);
                    colorPicker.show();
                    colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                        @Override
                        public void onChooseColor(int position, int color) {
                            // put code
                            toolbar.setBackground(new ColorDrawable(color));
                            header.setBackground(new ColorDrawable(color));
                            String hexColor = "#" + Integer.toHexString(color).substring(2);
                            col = hexColor;
                        }

                        @Override
                        public void onCancel() {
                            // put code
                        }
                    });
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Initialize fused location

        ArrayAdapter<String> adapterPerimeter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, perimeter);
        perimeter_spinner.setAdapter(adapterPerimeter);

        perimeter_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("500km")) {
                    if(namesOfFestivals.size()!=0)
                        namesOfFestivals.removeAll(namesOfFestivals);

                    festivals = helper.FestivalDao().getAll();
                    Location loc = new Location("");
                    loc.setLatitude(currentLatitude);
                    loc.setLongitude(currentLongitude);
                    Location loc1 = new Location("");
                    for (Festival festival : festivals) {
                        loc1.setLatitude(festival.gps_latitude);
                        loc1.setLongitude(festival.gps_longitude);
                        float distanceInMeters = loc.distanceTo(loc1);
                        float distanceInKm = distanceInMeters / 1000;
                        float dist = 500;
                        if (distanceInKm <= dist) {
                            namesOfFestivals.add(festival.name);
                        }
                    }
                    Toast.makeText(SettingsActivity.this, "" + namesOfFestivals.toString(), Toast.LENGTH_LONG).show();
                }
                if (selectedItem.equals("1000km")) {
                    // do your stuff
                    if(namesOfFestivals.size()!=0)
                        namesOfFestivals.removeAll(namesOfFestivals);

                    festivals = helper.FestivalDao().getAll();
                    Location loc = new Location("");
                    loc.setLatitude(currentLatitude);
                    loc.setLongitude(currentLongitude);
                    Location loc1 = new Location("");
                    for(Festival festival: festivals) {
                        loc1.setLatitude(festival.gps_latitude);
                        loc1.setLongitude(festival.gps_longitude);
                        float distanceInMeters = loc.distanceTo(loc1);
                        float distanceInKm = distanceInMeters/1000;
                        float dist = 1000;
                        if(distanceInKm <= dist) {
                            namesOfFestivals.add(festival.name);
                        }
                    }
                    strings = new String[namesOfFestivals.size()];
                    Toast.makeText(SettingsActivity.this, "" + namesOfFestivals.toString(), Toast.LENGTH_LONG).show();
                }
                if (selectedItem.equals("2000km")) {
                    if(namesOfFestivals.size()!=0)
                        namesOfFestivals.removeAll(namesOfFestivals);

                    festivals = helper.FestivalDao().getAll();
                    Location loc = new Location("");
                    loc.setLatitude(currentLatitude);
                    loc.setLongitude(currentLongitude);
                    Location loc1 = new Location("");
                    for(Festival festival: festivals) {
                        loc1.setLatitude(festival.gps_latitude);
                        loc1.setLongitude(festival.gps_longitude);
                        float distanceInMeters = loc.distanceTo(loc1);
                        float distanceInKm = distanceInMeters/1000;
                        float dist = 2000;
                        if(distanceInKm <= dist) {
                            namesOfFestivals.add(festival.name);
                        }
                    }
                    strings = new String[namesOfFestivals.size()];
                    Toast.makeText(SettingsActivity.this, "" + namesOfFestivals.toString(), Toast.LENGTH_LONG).show();
                }
                if (selectedItem.equals("5000km")) {
                    if(namesOfFestivals.size()!=0)
                        namesOfFestivals.removeAll(namesOfFestivals);

                    festivals = helper.FestivalDao().getAll();
                    Location loc = new Location("");
                    loc.setLatitude(currentLatitude);
                    loc.setLongitude(currentLongitude);
                    Location loc1 = new Location("");
                    for(Festival festival: festivals) {
                        loc1.setLatitude(festival.gps_latitude);
                        loc1.setLongitude(festival.gps_longitude);
                        float distanceInMeters = loc.distanceTo(loc1);
                        float distanceInKm = distanceInMeters/1000;
                        float dist = 5000;
                        if(distanceInKm <= dist) {
                            namesOfFestivals.add(festival.name);
                        }
                    }
                    strings = new String[namesOfFestivals.size()];
                    Toast.makeText(SettingsActivity.this, "" + namesOfFestivals.toString(), Toast.LENGTH_LONG).show();
                }
                if (selectedItem.equals("All")) {
                    if(namesOfFestivals.size()!=0)
                        namesOfFestivals.removeAll(namesOfFestivals);

                    festivals = helper.FestivalDao().getAll();
                    for(Festival festival: festivals) {
                        namesOfFestivals.add(festival.name);
                    }
                    strings = new String[namesOfFestivals.size()];
                    Toast.makeText(SettingsActivity.this, "" + namesOfFestivals.toString(), Toast.LENGTH_LONG).show();
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterDance = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dance);
        dance_spinner.setAdapter(adapterDance);

        dance_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();
                selectedDance = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this, MasterViewActivity.class);

                int i=0;
                for(String name: namesOfFestivals) {
                    strings[i] = name;
                    i++;
                }

                intent.putExtra("ApplyFestivalNames", strings);
                intent.putExtra("colorTheme", col.toString());
                intent.putExtra("danceType", selectedDance);
                startActivity(intent);
                intent.removeExtra("danceType");
                intent.removeExtra("colorTheme");
                intent.removeExtra("ApplyFestivalNames");
            }
        });


        liness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, NavigationActivity.class);
                intent.putExtra("colorTheme", col.toString());
                startActivity(intent);
                intent.removeExtra("colorTheme");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                intent.putExtra("colorTheme", col.toString());
                startActivity(intent);
                intent.removeExtra("colorTheme");
            }
        });

        imageHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, FavoritesFragment.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_festivals:
                Intent intent = new Intent(SettingsActivity.this, MasterViewActivity.class);

                intent.putExtra("ApplyFestivalNames", strings);
                intent.putExtra("colorTheme", col.toString());
                startActivity(intent);
                intent.removeExtra("colorTheme");
                intent.removeExtra("ApplyFestivalNames");
                break;
            case R.id.nav_map:
                b.putStringArray("ApplyFestivalNames", strings);
                b.putString("ColorTheme", col.toString());
                FragmentMaps fragmentMaps = new FragmentMaps();
                fragmentMaps.setArguments(b);
                // Toast.makeText(MasterViewActivity.this, "" + names.length, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragmentMaps).commit();
                break;
            case R.id.nav_favorites:
                startActivity(new Intent(getApplicationContext(), FavoritesFragment.class));
                break;
            case R.id.nav_Logout:

                User user = helper.UserDao().getLoggedInUser(true);
                user.loggedIn = false;
                helper.UserDao().updateUser(user);

                Intent intentOut = new Intent(SettingsActivity.this, LoginActivity.class);
                intentOut.putExtra("colorTheme", col.toString());
                intentOut.putExtra("ApplyFestivalNames", strings);
                startActivity(intentOut);
                intentOut.removeExtra("colorTheme");
                intentOut.removeExtra("ApplyFestivalNames");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    /**
     * If connected get lat and long
     */
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

           // Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

       // Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }
}
