package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Favorites;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.Notification;
import com.example.dance_world.database.entities.User;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MasterViewActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int RESULT_LOAD_IMAGE=1;
    private DatabaseHelper helper;
    private DrawerLayout drawer;
    private LinearLayout header;
    ImageButton settings, liness, imageHeart, imageAddPhoto, favorite;
    ListView ListViewFestival;
    TextView nameUser, nameFestival;
    ImageView image;
    Button notification;
    Toolbar toolbar;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;

     List<Button> buttons = new ArrayList<>();
     List<ImageButton> buttonsFav = new ArrayList<>();
     int[] images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);

        helper = DatabaseHelper.getInstance(this);

       // Favorites fav = new Favorites(helper.UserDao().getLoggedInUser(true).id, helper.FestivalDao().getFestivalByName("Summer Sensual Days").id);
      //  helper.FavoritesDao().insertFavorite(fav);

        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        ListViewFestival = findViewById(R.id.ListViewFestival);
        imageHeart = findViewById(R.id.heart);
        favorite = findViewById(R.id.favorite);
        notification = findViewById(R.id.notification);
        nameFestival = findViewById(R.id.nameFestival);
        toolbar = findViewById(R.id.toolbar);
        header = findViewById(R.id.header_color);

        final String color = getIntent().getStringExtra("colorTheme");
        ColorDrawable c = new ColorDrawable(Color.parseColor(color));
        toolbar.setBackground(c);

        final String names[] = getIntent().getStringArrayExtra("ApplyFestivalNames");

        if(names.length!=0) {
            int size = names.length;
            for(int i=0; i<size; i++){
                buttons.add(notification);
                buttonsFav.add(favorite);
            }

            int i = 0;
            images = new int[names.length];
            List<Festival> festivals = new ArrayList<>();
            for(String name: names) {
                Festival f = helper.FestivalDao().getFestivalByName(name);
                festivals.add(f);
            }
            for (Festival f : festivals) {
                int id = getResources().getIdentifier(f.imagePath, "drawable", getPackageName());
                images[i] = id;
                i++;
            }


            MyAdapter adapter = new MyAdapter(this, names, buttons, images, buttonsFav);
            ListViewFestival.setAdapter(adapter);
        }
        else {
            int size = helper.FestivalDao().getAll().size();
            for (int i = 0; i < size; i++) {
                buttons.add(notification);
                buttonsFav.add(favorite);
            }

            int i = 0;
            images = new int[helper.FestivalDao().getAll().size()];
            for (Festival f : helper.FestivalDao().getAll()) {
                int id = getResources().getIdentifier(f.imagePath, "drawable", getPackageName());
                images[i] = id;
                i++;
            }

            MyAdapter adapter = new MyAdapter(this, helper.FestivalDao().getAllNames(), buttons, images, buttonsFav);
            ListViewFestival.setAdapter(adapter);
        }
        //create adapter instance




        ListViewFestival.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String str = ListViewFestival.getItemAtPosition(position).toString();
                Intent intent = new Intent(MasterViewActivity.this, DetailActivity.class);
                Festival fest = helper.FestivalDao().getFestivalByName(str);

                intent.putExtra("festivalName", str);
                intent.putExtra("colorTheme", color);
                intent.putExtra("festivalImage", fest.imagePath);
                //Toast.makeText(MasterViewActivity.this, "" + Integer.parseInt("R.drawable.baltimore"), Toast.LENGTH_LONG).show();
                startActivity(intent);
                intent.removeExtra("festivalName");
                intent.removeExtra("colorTheme");
                intent.removeExtra("festivalImage");
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //customize header view
        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        nameUser = (TextView) header.findViewById(R.id.name);
        image = (ImageView) header.findViewById(R.id.image);
        imageAddPhoto = (ImageButton) header.findViewById(R.id.imageAddPhoto);
        User user = helper.UserDao().getLoggedInUser(true);
        nameUser.setText("Hello " + user.name + "!");
        Uri imgUri=Uri.parse(user.getImage());
        image.setImageURI(imgUri);

        header.setBackground(c);

        imageAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime premission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        //permission already granted
                        pickImageFromGallery();
                    }
                }else {
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }
            }
        });

        drawer = findViewById(R.id.drawer_layout);


        liness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterViewActivity.this, NavigationActivity.class);
                intent.putExtra("colorTheme", color);
                intent.putExtra("ApplyFestivalNames", names);
                startActivity(intent);
                intent.removeExtra("colorTheme");
                intent.removeExtra("ApplyFestivalNames");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterViewActivity.this, SettingsActivity.class);
                intent.putExtra("colorTheme", color);
                startActivity(intent);
                intent.removeExtra("colorTheme");
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
        List<Button> buttons;
        int rImgs[];
        List<ImageButton> buttonsFav;

        MyAdapter(Context c,  String title[],List<Button> btns,  int imgs[], List<ImageButton> fav) {
            super(c, R.layout.row_masterview,  R.id.nameFestival, title);
            this.context = c;
            this.rTitle = title;
            this.buttons = btns;
            this.rImgs = imgs;
            this.buttonsFav=fav;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View row = layoutInflater.inflate(R.layout.row_masterview, parent, false);
            ImageView images = row.findViewById(R.id.masterImage);
            final ImageButton myFav= row.findViewById(R.id.favorite);
            TextView myTitle = row.findViewById(R.id.nameFestival);
            Button myButtons= row.findViewById(R.id.notification);
            myTitle.setText(rTitle[position]);

            String s = ListViewFestival.getItemAtPosition(position).toString();
            Festival fest = helper.FestivalDao().getFestivalByName(s);
            for(Favorites f: helper.FavoritesDao().getAll()) {
                if (f.id_festival == fest.id) {
                    myFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }



            myButtons.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    String str = ListViewFestival.getItemAtPosition(position).toString();
                    LocalDateTime time = LocalDateTime.now();
                    User user = helper.UserDao().getLoggedInUser(true);
                    Notification notification = new Notification(time.toString(), str, user.id);
                    List<Notification> notifs = helper.NotificationDao().findNotificationsForUser(user.id);
                    String s = "No";
                    for(Notification n: notifs){
                        if(n.content.contains(str))
                            s = "Yes";
                    }
                    if(s=="No") {
                        helper.NotificationDao().insertNotification(notification);
                        Toast.makeText(getContext(), "You will receive notifications for that festival.", Toast.LENGTH_SHORT).show();
                        onRestart();
                    }
                }
            });

            myFav.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    String str = ListViewFestival.getItemAtPosition(position).toString();
                    LocalDateTime time = LocalDateTime.now();
                    User user = helper.UserDao().getLoggedInUser(true);
                    Festival festival = helper.FestivalDao().getFestivalByName(str);
                    Favorites favorites = new Favorites(user.id, festival.id);
                    String st = "Add";

                    if(helper.FavoritesDao().getAll().size()==0) {
                        st = "Add to favorites";
                    }

                    for(Favorites f: helper.FavoritesDao().getAll()){
                        if(f.id_user==user.id && f.id_festival==festival.id)
                            st = "Already is favorite";
                    }

                    if(st=="Add to favorites" || st=="Add") {
                        helper.FavoritesDao().insertFavorite(favorites);
                        onRestart();
                    }
                    else
                        Toast.makeText(getContext(), "Already is favorite", Toast.LENGTH_SHORT).show();

                    myFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    //Toast.makeText(getContext(), "" + user.id + festival.id, Toast.LENGTH_SHORT).show();
                }
           });

            images.setImageResource(rImgs[position]);
           // myFav.setBottom(buttonsFav[position]);

        //    myButtons.setBottom(buttons[position]);
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

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else{
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            image.setImageURI(data.getData());
            User user = helper.UserDao().getLoggedInUser(true);
            user.setImage(data.getDataString());
            helper.UserDao().updateUser(user);
        }
    }

    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
    }

}
