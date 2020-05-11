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

import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    Spinner theme_spinner, perimeter_spinner, dance_spinner;
    Button Apply;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        theme_spinner = findViewById(R.id.theme_spinner);
        perimeter_spinner = findViewById(R.id.perimeter_spinner);
        dance_spinner = findViewById(R.id.dance_spinner);
        Apply = findViewById(R.id.Apply);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavoritesFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
