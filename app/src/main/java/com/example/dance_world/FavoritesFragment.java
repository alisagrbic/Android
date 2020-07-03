package com.example.dance_world;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Favorites;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends AppCompatActivity {

    ListView listFavorites;
    String festivalNames[];
    String festivalCities[];
    int festivalImages[];
    Favorites fav;
    Festival fest;
    ImageButton buttonDeleteFavorite;
    TextView name;
    MyAdapter adapter;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites);

        helper = DatabaseHelper.getInstance(this);

        User user = helper.UserDao().getLoggedInUser(true);
        List<Favorites> favorites = helper.FavoritesDao().getAllFavoritesByUserId(user.id);

        festivalNames = new String[favorites.size()];
        festivalCities = new String[favorites.size()];
        festivalImages = new int[favorites.size()];

        if(favorites!=null) {
            for (int i = 0; i < favorites.size(); i++) {
                fav = favorites.get(i);
                fest = helper.FestivalDao().getFestivalById(fav.id_festival);
                festivalNames[i] = fest.name;
                festivalCities[i] = fest.city;
                festivalImages[i] = fest.imagePath;
            }
        }

        listFavorites = findViewById(R.id.listFavorites);
        buttonDeleteFavorite = findViewById(R.id.buttonDeleteFavorite);
        name = findViewById(R.id.name);

        //create adapter instance
        adapter = new MyAdapter(this, festivalNames, festivalCities, festivalImages);
        listFavorites.setAdapter(adapter);

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImg[];

        MyAdapter(Context c, String title[],String description[], int img[]) {
            super(c, R.layout.row_favorites,R.id.name, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImg = img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_favorites, parent, false);
            TextView myTitle = row.findViewById(R.id.name);
            ImageView images = row.findViewById(R.id.fav_image);
            TextView myDescription= row.findViewById(R.id.subtitle);

            images.setImageResource(rImg[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            return row;
        }
    }
}
