package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dance_world.database.entities.Artist;

public class DetailActivity extends AppCompatActivity {

    ImageButton settings, liness;
    Button artist, dj, workshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        artist = findViewById(R.id.artist);
        dj = findViewById(R.id.dj);
        workshop = findViewById(R.id.workshop);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        liness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
        artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ArtistsActivity.class);
                startActivity(intent);
            }
        });
        dj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, DjActivity.class);
                startActivity(intent);
            }
        });
        workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WorkshopActivity.class);
                startActivity(intent);
            }
        });
    }
}
