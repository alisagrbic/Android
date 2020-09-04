package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.lang.reflect.Field;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseHelper helper;
    ImageButton settings, liness, imageHeart;
    ListView listView;
    private DrawerLayout drawer;
    Festival festival, festivalMaster;
    Toolbar toolbar;
    ImageView image;
    String color = "";
    String names[] = {};
    Bundle b = new Bundle();


    int images[] = {R.drawable.artists, R.drawable.dj2, R.drawable.workshop};
    String mTitle[] = {"ARTIST", "DJ", "WORKSHOP"};

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        helper = DatabaseHelper.getInstance(this);
        String festivalName = getIntent().getStringExtra("festivalName");
        String festivalImage = getIntent().getStringExtra("festivalImage");
        names = getIntent().getStringArrayExtra("ApplyFestivalNames");

        festival = helper.FestivalDao().getFestivalByName(festivalName);

        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        listView = findViewById(R.id.ListView);
        imageHeart = findViewById(R.id.heart);
        toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.image);

        color = getIntent().getStringExtra("colorTheme");
        ColorDrawable c = new ColorDrawable(Color.parseColor(color));

        toolbar.setBackground(c);

        int id = getResources().getIdentifier(festivalImage, "drawable", getPackageName());
        image.setImageResource(id);


        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, images);
        listView.setAdapter(adapter);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //customize header view
        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        header.setBackground(c);

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
                    intent.putExtra("festivalId", String.valueOf(festival.id));
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
                intent.putExtra("colorTheme", color);
                startActivity(intent);
                intent.removeExtra("colorTheme");
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
                intent.putExtra("ApplyFestivalNames", names);
                intent.putExtra("colorTheme", color);
                startActivity(intent);
                intent.removeExtra("colorTheme");
                intent.removeExtra("ApplyFestivalNames");
                break;
            case R.id.nav_map:
                b.putStringArray("ApplyFestivalNames", names);
                b.putString("ColorTheme", color);
                FragmentMaps fragmentMaps = new FragmentMaps();
                fragmentMaps.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragmentMaps).commit();
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
