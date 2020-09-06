package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Artist;
import com.example.dance_world.database.entities.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class ArtistsActivity extends AppCompatActivity {

    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
    private DatabaseHelper helper;
    ImageButton openDialog;
    List<Artist> artists;
    ListView listView;
    String mTitle[];
    String mDescription[];
    int images[];
    ImageView uploadedImage;
    ImageButton imageAddPhoto;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    Artist newArtist = new Artist();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);
        helper = DatabaseHelper.getInstance(this);
        mRequestQue = Volley.newRequestQueue(this);
        openDialog = findViewById(R.id.openAddArtistDialog);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        final String festivalId = getIntent().getStringExtra("festivalId");
        artists = helper.ArtistDao().getArtistByFestivalId(Long.parseLong(festivalId));
        imageAddPhoto = (ImageButton) findViewById(R.id.imageAddPhoto);

        listView = findViewById(R.id.ListView);

        int i = 0;
        mTitle = new String[artists.size()];
        mDescription = new String[artists.size()];
        images = new int[artists.size()];
        for (Artist a: artists) {
            mTitle[i] = a.name;
            mDescription[i] = a.surname;
            images[i] = a.imagePath;
            i++;
        };

        //create adapter instance
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);



        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View viewDialog = LayoutInflater.from(ArtistsActivity.this).inflate(R.layout.add_artist_dialog, null);
                final EditText editText = (EditText) viewDialog.findViewById(R.id.artistName);
                imageAddPhoto = (ImageButton) viewDialog.findViewById(R.id.imageAddPhoto);
                uploadedImage = viewDialog.findViewById(R.id.image);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(ArtistsActivity.this)
                        .setView(viewDialog)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SendNotification();
                                String artistName = editText.getText().toString();
                                newArtist.name = artistName;
                                newArtist.id_festival = Long.parseLong(festivalId);
                                helper.ArtistDao().insertArtist(newArtist);
                            }

                        });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    private void SendNotification() {
        JSONObject jsonObject = new JSONObject();
       // jsonObject = null;
        try {
            jsonObject.put("to", "/topics/" + "news");
            JSONObject notObj = new JSONObject();
            notObj.put("title","Artist on festival");
            notObj.put("body","Look which artist is arrive on festival!");
            jsonObject.put("notification",notObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //code here will run on success
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //code here sill run on error
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization", "key=AAAA4oFZots:APA91bGlq1T3qI1eKrJ6cCFHeCa3EAGM5tpdsLJeJEsgC1U-xPoE8tL5-acpB_iEV6nc3w-EicN0VO2doW8qhG1UqwFVJbsW6irT7C3L4JL0TUH3_v3hXADyY1SlZ1vZdl62nZBd-Uom");
                    return header;
                }
            };
            mRequestQue.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

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
            uploadedImage.setImageURI(data.getData());
            int a = this.getResources().getIdentifier(String.valueOf(data.getData()), "drawable", getPackageName());
            newArtist.setImagePath(data.filterHashCode());
        }
    }

    class MyAdapter extends ArrayAdapter<String>{
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
