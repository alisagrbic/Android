package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.DatabaseInitializer;
import com.example.dance_world.database.entities.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    Button loginMain;
    DatabaseInitializer dbInitializer;
  //  private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//    dbInitializer = new DatabaseInitializer(this);
//    dbInitializer.InitData();
     //   Toolbar toolbar = findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);|

        loginMain = findViewById(R.id.loginMain);

        loginMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
