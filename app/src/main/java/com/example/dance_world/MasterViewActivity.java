package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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



public class MasterViewActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int RESULT_LOAD_IMAGE=1;
    private DatabaseHelper helper;
    private DrawerLayout drawer;
    ImageButton settings, liness, imageHeart, imageAddPhoto;
    ListView listView;
    TextView nameUser;
    ImageView image;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;


    int images[]; //= {R.drawable.rovinj, R.drawable.rovinj2};
    /* String mTitle[] = {"Receive notifications?", "Receive notifications?"};*/
    int buttons[]= {R.id.notification, R.id.notification, R.id.notification, R.id.notification,R.id.notification};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);

        helper = DatabaseHelper.getInstance(this);

        settings = findViewById(R.id.settings);
        liness = findViewById(R.id.liness);
        listView = findViewById(R.id.ListView);
        imageHeart = findViewById(R.id.heart);

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, helper.FestivalDao().getAllNames(), buttons, helper.FestivalDao().getAllImages());
        listView.setAdapter(adapter);

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

}
