package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.DatabaseInitializer;
import com.example.dance_world.database.entities.Artist;
import com.example.dance_world.database.entities.Dj;
import com.example.dance_world.database.entities.Favorites;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.Notification;
import com.example.dance_world.database.entities.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button loginMain;
    DatabaseInitializer dbInitializer;
  //  private DrawerLayout drawer;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   // dbInitializer = new DatabaseInitializer(this);
   // dbInitializer.InitData();
     //   Toolbar toolbar = findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);|

        helper = DatabaseHelper.getInstance(this);

       /* for(Artist ar: helper.ArtistDao().getAll())
            helper.ArtistDao().deleteArtist(ar);

        for(Dj dj: helper.DjDao().getAll())
            helper.DjDao().deleteDj(dj);


        for(Favorites fav: helper.FavoritesDao().getAll())
            helper.FavoritesDao().deleteFavorites(fav);

        for(Festival f: helper.FestivalDao().getAll())
            helper.FestivalDao().deleteFestival(f);

        Toast.makeText(MainActivity.this, "" + helper.FestivalDao().getAll().size(), Toast.LENGTH_SHORT).show();*/

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
