package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ToolbarActivity extends AppCompatActivity {

    ImageButton liness, settings,imageheart;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        liness = findViewById(R.id.liness);
        settings = findViewById(R.id.settings);
        imageheart = findViewById(R.id.heart);
        toolbar = findViewById(R.id.toolbar);

        String color = getIntent().getStringExtra("colorTheme");
        ColorDrawable c = new ColorDrawable(Color.parseColor(color));

        toolbar.setBackground(c);


        liness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToolbarActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToolbarActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        imageheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToolbarActivity.this, FavoritesFragment.class);
                startActivity(intent);
            }
        });
    }
}
