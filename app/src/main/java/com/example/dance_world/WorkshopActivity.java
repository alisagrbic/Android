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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class WorkshopActivity extends AppCompatActivity {

    ListView listView;
    ImageButton openDialog;
    String mTitle[] = {"Kizomba", "Sensual Bachata", "Kizomba Fusion"};
    String mDescription[] = {"Slow moves, slides and lifts \n\n19. June 13:00-14:00",
    "Fundamentals \n\n19. June 14:10-15:10", "Fundamentals and evlolution \n\n19. June 15:20-16:20"};
    Toolbar toolbar;
    int images[] = {R.drawable.kizomba, R.drawable.bachata, R.drawable.kiz};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        listView = findViewById(R.id.ListView);
        toolbar = findViewById(R.id.toolbar);

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);
    }

    class MyAdapter extends ArrayAdapter<String> {
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
