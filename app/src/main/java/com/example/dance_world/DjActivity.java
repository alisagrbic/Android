package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DjActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = {"DJ Khalid", "DJ El Tiquere", "DJ Latin Master", "DJ X-tra"};
    String mDescription[] = {"Bachata", "Bachata", "Bachata, Salsa Romantica", "Kizomba"};
    int images = R.drawable.dj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);

        listView = findViewById(R.id.ListView);

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImg;

        MyAdapter(Context c, String title[], String description[], int img) {
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

            images.setImageResource(rImg);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }
    }
}
