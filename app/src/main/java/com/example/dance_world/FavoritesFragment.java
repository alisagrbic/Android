package com.example.dance_world;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FavoritesFragment extends AppCompatActivity {

    ListView listFavorites;
    String mTitle[] = {"Summer sensual days Rovinj"};
    String mDescription[] = {"Rovinj"};
    int images = R.drawable.rovinj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites);

        listFavorites = findViewById(R.id.listFavorites);

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listFavorites.setAdapter(adapter);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImg;

        MyAdapter(Context c, String title[],String description[], int img) {
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

            images.setImageResource(rImg);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            return row;
        }
    }
}
