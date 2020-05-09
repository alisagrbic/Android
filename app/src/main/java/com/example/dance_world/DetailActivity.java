package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dance_world.database.entities.Artist;

public class DetailActivity extends AppCompatActivity {

    ImageButton settings, liness;
    ListView listView;


    int images[] = {R.drawable.artists, R.drawable.dj2, R.drawable.workshop};
    String mTitle[] = {"ARTIST", "DJ", "WORKSHOP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        listView = findViewById(R.id.ListView);

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent intent = new Intent(DetailActivity.this, ArtistsActivity.class);
                    startActivity(intent);
                }
                else if(position==1) {
                    Intent intent = new Intent(DetailActivity.this, DjActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(DetailActivity.this, WorkshopActivity.class);
                    startActivity(intent);
                }
            }
        });

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
    }


    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        int rImgs[];

        MyAdapter(Context c,  String title[], int imgs[]) {
            super(c, R.layout.row_detail, R.id.name, title);
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row_detail, parent, false);
            ImageView images = row.findViewById(R.id.detailImage);
            TextView myTitle = row.findViewById(R.id.name);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);

            return row;
        }
    }
}
