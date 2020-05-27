package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.User;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MasterViewActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper helper;
    private DrawerLayout drawer;
    ImageButton settings, liness, imageHeart;
    ListView listView;


    int images[]; //= {R.drawable.rovinj, R.drawable.rovinj2};
    /* String mTitle[] = {"Receive notifications?", "Receive notifications?"};*/
    int buttons[]= {R.id.notification, R.id.notification, R.id.notification, R.id.notification,R.id.notification, R.id.notification, R.id.notification, R.id.notification, R.id.notification, R.id.notification};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);

        helper = DatabaseHelper.getInstance(this);

        // User u = helper.UserDao().getUserByUsername("Alisic");
        // User u1 = helper.UserDao().getUserByUsername("Majce");
        // User u2 = helper.UserDao().getUserByUsername("Mica");


//ADD ENTITIES
/*
       //Festivals
      Festival f1 = new Festival("Creamfields", "Austria", 48.210033, 16.363449, "One of the two or three most prestigious dance festivals on the planet, Creamfields is always full to bursting with worldwide stars.",
                "27.08.2020.", u.id, "R.drawable.creamfields");
        helper.FestivalDao().insertFestival(f1);
        Festival f2 = new Festival("EXIT", "Novi Sad", 45.267136, 19.833549, "EXIT is an award-winning summer music festival that takes place at the Petrovaradin Fortress in Novi Sad, Serbia, with more than 1000 artists who play at over 40 stages and festival zones.",
                "08.07.2020.", u1.id, "R.drawable.exit");
        helper.FestivalDao().insertFestival(f2);
        Festival f3 = new Festival("Primavera Sound", "Beograd", 44.787197, 20.457273, "Indie King and forceful supporter of new music, Primavera Sound prides itself on pushing the alternative scene forward. ",
                "20.10.2020.", u2.id, "R.drawable.primavera");
        helper.FestivalDao().insertFestival(f3);
        Festival f4 = new Festival("Ultra Europe", "Split", 43.508133, 16.440193, "Transported across the Atlantic from Miami's glamourous city centre, Ultra Europe wasted no time in establishing itself as a worthy successor to the original UMF. ",
                "10.08.2020.", u.id, "R.drawable.ultra");
        helper.FestivalDao().insertFestival(f4);
        Festival f5 = new Festival("Sziget Festival", "Hungary", 47.497913, 19.040236, "Sziget is one of those festivals that could never spend another cent on marketing and still sell out every single year.",
                "17.08.2020.", u1.id, "R.drawable.sziget");
        helper.FestivalDao().insertFestival(f5);
        Festival f6 = new Festival("Mad Cool Festival", "Madrid", 47.497913, 19.040236, "Only a few years old, Mad Cool is already one of Europe's most glittering, glaringly popular festivals.",
                "1.08.2020.", u2.id, "R.drawable.madcool");
        helper.FestivalDao().insertFestival(f6);

      //Djs
        Dj dj1 = new Dj("DJ Khalid", "Bachata", helper.FestivalDao().getIdFestivalByName("Creamfields"));
        Dj dj18 = new Dj("DJ Khalid", "Bachata", helper.FestivalDao().getIdFestivalByName("Ultra Europe"));
        Dj dj17 = new Dj("DJ Khalid", "Bachata", helper.FestivalDao().getIdFestivalByName("Sziget Festival"));
        Dj dj2 = new Dj("DJ Khalid", "Bachata", helper.FestivalDao().getIdFestivalByName("EXIT"));
        Dj dj3 = new Dj("DJ El Tiquere", "Bachata", helper.FestivalDao().getIdFestivalByName("Primavera Sound"));
        Dj dj4 = new Dj("DJ El Tiquere", "Bachata", helper.FestivalDao().getIdFestivalByName("EXIT"));
        Dj dj5 = new Dj("DJ El Tiquere", "Bachata", helper.FestivalDao().getIdFestivalByName("Sziget Festival"));
        Dj dj6 = new Dj("DJ Latin Master", "Bachata, Salsa Romantica", helper.FestivalDao().getIdFestivalByName("Creamfields"));
        Dj dj = new Dj("DJ Latin Master", "Bachata, Salsa Romantica", helper.FestivalDao().getIdFestivalByName("Mad Cool Festival"));
        Dj dj7 = new Dj("DJ Latin Master", "Bachata, Salsa Romantica", helper.FestivalDao().getIdFestivalByName("EXIT"));
        Dj dj8 = new Dj("DJ X-tra", "Kizomba", helper.FestivalDao().getIdFestivalByName("Creamfields"));
        Dj dj0 = new Dj("DJ X-tra", "Kizomba", helper.FestivalDao().getIdFestivalByName("Sziget Festival"));
        Dj dj14 = new Dj("DJ X-tra", "Kizomba", helper.FestivalDao().getIdFestivalByName("Primavera Sound"));
        Dj dj13 = new Dj("DJ X-tra", "Kizomba", helper.FestivalDao().getIdFestivalByName("Mad Cool Festival"));
      //Artist
        Artist a1 = new Artist("Daniel & Desiree", "Bachata Sensual", helper.FestivalDao().getIdFestivalByName("Creamfields"), "R.drawable.daniel_desiree");
        Artist a14 = new Artist("Daniel & Desiree", "Bachata Sensual", helper.FestivalDao().getIdFestivalByName("Ultra Europe"), "R.drawable.daniel_desiree");
        Artist a16 = new Artist("Daniel & Desiree", "Bachata Sensual", helper.FestivalDao().getIdFestivalByName("Sziget Festival"), "R.drawable.daniel_desiree");

        Artist a2 = new Artist("Daniel & Desiree", "Bachata Sensual", helper.FestivalDao().getIdFestivalByName("EXIT"), "R.drawable.daniel_desiree");
        Artist a3 = new Artist("Daniel & Desiree", "Bachata Sensual", helper.FestivalDao().getIdFestivalByName("Primavera Sound"), "R.drawable.daniel_desiree");
        Artist a4 = new Artist("Ronald & Alba", "Bachata", helper.FestivalDao().getIdFestivalByName("Creamfields"), "R.drawable.ronald_alba");
        Artist a44 = new Artist("Ronald & Alba", "Bachata", helper.FestivalDao().getIdFestivalByName("Ultra Europe"), "R.drawable.ronald_alba");
        Artist a45 = new Artist("Ronald & Alba", "Bachata", helper.FestivalDao().getIdFestivalByName("Mad Cool Festiva"), "R.drawable.ronald_alba");


        Artist a5 = new Artist("Ronald & Alba", "Bachata", helper.FestivalDao().getIdFestivalByName("EXIT"), "R.drawable.ronald_alba");
        Artist a6 = new Artist("Ronald & Alba", "Bachata ", helper.FestivalDao().getIdFestivalByName("Primavera Sound"), "R.drawable.ronald_alba");
        Artist a7 = new Artist("Korke & Judit", "Salsa", helper.FestivalDao().getIdFestivalByName("Creamfields"), "R.drawable.korke_judit");
        Artist a8 = new Artist("Korke & Judit", "Salsa", helper.FestivalDao().getIdFestivalByName("Primavera Sound"), "R.drawable.korke_judit");
        Artist a88 = new Artist("Korke & Judit", "Salsa", helper.FestivalDao().getIdFestivalByName("Ultra Europe"), "R.drawable.korke_judit");
        Artist a89 = new Artist("Korke & Judit", "Salsa", helper.FestivalDao().getIdFestivalByName("Sziget Festival"), "R.drawable.korke_judit");

        Artist a9 = new Artist("Ataca & La Alemana", "Bachata modern", helper.FestivalDao().getIdFestivalByName("Creamfields"), "R.drawable.ataca_laalemana");
        Artist a12 = new Artist("Ataca & La Alemana", "Bachata modern", helper.FestivalDao().getIdFestivalByName("EXIT"), "R.drawable.ataca_laalemana");
        Artist a10 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("EXIT"), "R.drawable.luis_andrea");
        Artist a11 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("Primavera Sound"), "R.drawable.luis_andrea");
        Artist a111 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("Sziget Festival"), "R.drawable.luis_andrea");
        Artist a113 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("Mad Cool Festiva"), "R.drawable.luis_andrea");

*/
        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        listView = findViewById(R.id.ListView);
        imageHeart = findViewById(R.id.heart);

       /* int sizeList = helper.FestivalDao().getAll().size();
        String s=String.valueOf(sizeList);
        Toast.makeText(MasterViewActivity.this, s, Toast.LENGTH_SHORT).show();*/

        /*for(int i=0; i<sizeList; i++){
            buttons[i]=R.id.notification;
        }*/


        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, helper.FestivalDao().getAllNames(), buttons, helper.FestivalDao().getAllImages());
        listView.setAdapter(adapter);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MasterViewActivity.this, DetailActivity.class);
                    startActivity(intent);
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

        imageHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterViewActivity.this, FavoritesFragment.class);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_festivals:
                Intent intent = new Intent(MasterViewActivity.this, MasterViewActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMaps()).commit();
                     break;
            case R.id.nav_favorites:
                Intent intentFav = new Intent(MasterViewActivity.this, FavoritesFragment.class);
                startActivity(intentFav);
                break;
            case R.id.nav_Logout:

                User user = helper.UserDao().getLoggedInUser(true);
                user.loggedIn=false;
                helper.UserDao().updateUser(user);

                Intent intentOut = new Intent(MasterViewActivity.this, LoginActivity.class);
                startActivity(intentOut);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
