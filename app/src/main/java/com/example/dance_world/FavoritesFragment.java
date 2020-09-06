package com.example.dance_world;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Favorites;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends AppCompatActivity {

    ListView listFavorites;
    String festivalNames[];
    String festivalCities[];
    int festivalImages[];
    List<ImageButton> buttonFav = new ArrayList<ImageButton>(); //= {R.id.buttonDeleteFavorite,R.id.buttonDeleteFavorite,R.id.buttonDeleteFavorite};
    Favorites fav;
    Festival fest;
    ImageButton buttonDeleteFavorite;
    TextView name;
    MyAdapter adapter;
    private DatabaseHelper helper;
    Toolbar toolbar;
    String color = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites);

        helper = DatabaseHelper.getInstance(this);

        User user = helper.UserDao().getLoggedInUser(true);
        List<Favorites> favorites = helper.FavoritesDao().getAllFavoritesByUserId(user.id);


        buttonDeleteFavorite = findViewById(R.id.buttonDeleteFavorite);
        toolbar = findViewById(R.id.toolbar);


        festivalNames = new String[favorites.size()];
        festivalCities = new String[favorites.size()];
        festivalImages = new int[favorites.size()];

        if(favorites!=null) {
            for (int i = 0; i < favorites.size(); i++) {
                fav = favorites.get(i);
                fest = helper.FestivalDao().getFestivalById(fav.id_festival);
                festivalNames[i] = fest.name;
                festivalCities[i] = fest.city;
                int id = getResources().getIdentifier(fest.imagePath, "drawable", getPackageName());
                festivalImages[i] = id;
                buttonFav.add(buttonDeleteFavorite);
            }
        }

       /* int size = favorites.size();

        for(int i=0; i<size; i++){
            buttonFav[i]=R.id.buttonDeleteFavorite;

        }*/

      //  Toast.makeText(FavoritesFragment.this, "" + buttonFav, Toast.LENGTH_SHORT).show();
        listFavorites = findViewById(R.id.listFavorites);

        name = findViewById(R.id.name);

        //create adapter instance
        adapter = new MyAdapter(this, festivalNames, festivalCities, festivalImages, buttonFav);
        listFavorites.setAdapter(adapter);

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImg[];
        List<ImageButton> bFav;

        MyAdapter(Context c, String title[],String description[], int img[], List<ImageButton> fav) {
            super(c, R.layout.row_favorites,R.id.name, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImg = img;
            this.bFav = fav;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_favorites, parent, false);
            TextView myTitle = row.findViewById(R.id.name);
            ImageView images = row.findViewById(R.id.fav_image);
            TextView myDescription= row.findViewById(R.id.subtitle);
            ImageButton myButtons= row.findViewById(R.id.buttonDeleteFavorite);

            myButtons.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    String str = listFavorites.getItemAtPosition(position).toString();
                    User user = helper.UserDao().getLoggedInUser(true);
                    Festival festival = helper.FestivalDao().getFestivalByName(str);

                    for(Favorites f: helper.FavoritesDao().getAll()) {
                        if (f.id_user == user.id && f.id_festival == festival.id) {
                            helper.FavoritesDao().deleteFavorites(f);
                            onRestart();
                        }

                    }

                   // myFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    //Toast.makeText(getContext(), "" + user.id + festival.id, Toast.LENGTH_SHORT).show();
                }
            });


            images.setImageResource(rImg[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
          //  myButtons.setBottom(bFav.get(position).getBottom());

            return row;
        }
    }

    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
    }
}
