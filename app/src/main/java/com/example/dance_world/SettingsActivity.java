package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner theme_spinner, perimeter_spinner, dance_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        theme_spinner = findViewById(R.id.theme_spinner);
        perimeter_spinner = findViewById(R.id.perimeter_spinner);
        dance_spinner = findViewById(R.id.dance_spinner);

        String[] theme = new String[]{ "App theme", "White", "Black"};
        String[] perimeter = new String[]{ "Perimeter", "50km", "100km", "500km", "1000km"};
        String[] dance = new String[]{ "Dance", "Banchata", "Kizomba", "Salsa"};

        ArrayAdapter<String> adapterTheme = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, theme);
        theme_spinner.setAdapter(adapterTheme);

        ArrayAdapter<String> adapterPerimeter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, perimeter);
        perimeter_spinner.setAdapter(adapterPerimeter);

        ArrayAdapter<String> adapterDance = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dance);
        dance_spinner.setAdapter(adapterDance);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
