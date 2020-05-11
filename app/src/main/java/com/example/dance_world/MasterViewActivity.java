package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.dance_world.database.DatabaseHelper;
import com.google.android.material.navigation.NavigationView;


public class MasterViewActivity  extends AppCompatActivity {

    ImageButton settings, liness;
    ListView listView;


    int images[] = {R.drawable.rovinj, R.drawable.rovinj2};
    String mTitle[] = {"Receive notifications?", "Receive notifications?"};
    int buttons[] = {R.id.notification, R.id.notification};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);


        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        listView = findViewById(R.id.ListView);

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, buttons, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MasterViewActivity.this, DetailActivity.class);
                    startActivity(intent);
               // Toast.makeText(MasterViewActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });


        liness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterViewActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterViewActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        int buttons[];
        int rImgs[];

        MyAdapter(Context c,  String title[], int btns[],  int imgs[]) {
            super(c, R.layout.row_masterview, R.id.notification, title);
            this.context = c;
            this.rTitle = title;
            this.buttons = btns;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row_masterview, parent, false);
            ImageView images = row.findViewById(R.id.masterImage);
            TextView myTitle = row.findViewById(R.id.name);
            Button myButtons= row.findViewById(R.id.notification);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myButtons.setBottom(buttons[position]);

            return row;
        }
    }

}
