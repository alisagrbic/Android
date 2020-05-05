package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dance_world.database.DatabaseHelper;



public class MasterViewActivity  extends AppCompatActivity {

    private DatabaseHelper helper;
    Button ReciveNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);



        ReciveNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterViewActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
