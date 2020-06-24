package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.User;
import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper helper;
    Spinner theme_spinner, perimeter_spinner, dance_spinner;
    Button Apply;
    private DrawerLayout drawer;
    ImageButton settings, liness, imageHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        helper = DatabaseHelper.getInstance(this);

        theme_spinner = findViewById(R.id.theme_spinner);
        perimeter_spinner = findViewById(R.id.perimeter_spinner);
        dance_spinner = findViewById(R.id.dance_spinner);
        Apply = findViewById(R.id.Apply);
        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        imageHeart = findViewById(R.id.heart);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //customize header view
        View header = navigationView.inflateHeaderView(R.layout.nav_header);

        drawer = findViewById(R.id.drawerr_layout);


        String[] theme = new String[]{ "App theme", "White", "Black"};
        String[] perimeter = new String[]{ "Perimeter", "50km", "100km", "500km", "1000km"};
        String[] dance = new String[]{ "Dance", "Banchata", "Kizomba", "Salsa"};

        ArrayAdapter<String> adapterTheme = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, theme);
        theme_spinner.setAdapter(adapterTheme);

        ArrayAdapter<String> adapterPerimeter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, perimeter);
        perimeter_spinner.setAdapter(adapterPerimeter);

        ArrayAdapter<String> adapterDance = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dance);
        dance_spinner.setAdapter(adapterDance);

        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MasterViewActivity.class);
                startActivity(intent);
            }
        });


        liness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
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
        switch (menuItem.getItemId()){
            case R.id.nav_festivals:
                Intent intent = new Intent(SettingsActivity.this, MasterViewActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMaps()).commit();
                break;
            case R.id.nav_favorites:
                startActivity(new Intent(getApplicationContext(), FavoritesFragment.class));
                break;
            case R.id.nav_Logout:

                User user = helper.UserDao().getLoggedInUser(true);
                user.loggedIn=false;
                helper.UserDao().updateUser(user);

                Intent intentOut = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intentOut);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
