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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;
import com.google.android.material.navigation.NavigationView;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseHelper helper;
    ImageButton settings, liness, imageHeart;
    ListView listView;
    private DrawerLayout drawer;
    Festival festival, festivalMaster;


    int images[] = {R.drawable.artists, R.drawable.dj2, R.drawable.workshop};
    String mTitle[] = {"ARTIST", "DJ", "WORKSHOP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        helper = DatabaseHelper.getInstance(this);
        String festivalCity = getIntent().getStringExtra("festivalCity");
        festival = helper.FestivalDao().getFestivalByCity(festivalCity);

        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        listView = findViewById(R.id.ListView);
        imageHeart = findViewById(R.id.heart);

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, images);
        listView.setAdapter(adapter);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //customize header view
        View header = navigationView.inflateHeaderView(R.layout.nav_header);

        drawer = findViewById(R.id.drawerr_layout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent intent = new Intent(DetailActivity.this, ArtistsActivity.class);
                    intent.putExtra("festivalId", String.valueOf(festival.id));
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

        imageHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, FavoritesFragment.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_festivals:
                Intent intent = new Intent(DetailActivity.this, MasterViewActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMaps()).commit();
                break;
            case R.id.nav_favorites:
                startActivity(new Intent(getApplicationContext(), FavoritesFragment.class));
                break;
            case R.id.nav_Logout:

                User user = helper.UserDao().getLoggedInUser(true);
                user.loggedIn=false;
                helper.UserDao().updateUser(user);

                Intent intentOut = new Intent(DetailActivity.this, LoginActivity.class);
                startActivity(intentOut);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
