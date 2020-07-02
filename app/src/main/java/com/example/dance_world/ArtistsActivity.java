package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Artist;

import java.util.List;

public class ArtistsActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    List<Artist> artists;
    ListView listView;
    String mTitle[];
    String mDescription[];
    int images[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = DatabaseHelper.getInstance(this);
        String festivalId = getIntent().getStringExtra("festivalId");
        artists = helper.ArtistDao().getArtistByFestivalId(Long.parseLong(festivalId));
        setContentView(R.layout.activity_artists);

        listView = findViewById(R.id.ListView);

        int i = 0;
        mTitle = new String[artists.size()];
        mDescription = new String[artists.size()];
        images = new int[artists.size()];
        for (Artist a: artists) {
            mTitle[i] = a.name;
            mDescription[i] = a.surname;
            images[i] = a.imagePath;
            i++;
        };

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter(Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.name, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.artistImage);
            TextView myTitle = row.findViewById(R.id.name);
            TextView myDescription= row.findViewById(R.id.subtitle);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }
    }
}
