package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Artist;
import com.example.dance_world.database.entities.Dj;

import java.util.List;

public class DjActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    ListView listView;
    String mTitle[];
    String mDescription[];
    int images [];
    Toolbar toolbar;
    List<Dj> djs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);

        helper = DatabaseHelper.getInstance(this);

        listView = findViewById(R.id.ListView);
        toolbar = findViewById(R.id.toolbar);

        String festivalId = getIntent().getStringExtra("festivalId");
        djs = helper.DjDao().getDjsByFestivalId(Long.parseLong(festivalId));

        int i = 0;
        mTitle = new String[djs.size()];
        mDescription = new String[djs.size()];
        images = new int[djs.size()];
        for (Dj d: djs) {
            mTitle[i] = d.name;
            mDescription[i] = d.surname;
            images[i] = R.drawable.dj2;
            i++;
        };


        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImg[];

        MyAdapter(Context c, String title[], String description[], int img[]) {
            super(c, R.layout.row, R.id.name, title);
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

            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.artistImage);
            TextView myTitle = row.findViewById(R.id.name);
            TextView myDescription= row.findViewById(R.id.subtitle);

            images.setImageResource(rImg[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }
    }
}
