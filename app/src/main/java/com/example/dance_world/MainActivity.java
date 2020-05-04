package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.dance_world.database.DatabaseHelper;



public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    ListView List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = DatabaseHelper.getInstance(this);
    /*    User user = new User("Alisa", "Grbic", "Alisic", "alisa.grbic@gmail.com", "alisa123", 0);
        helper.UserDao().insertUser(user);*/

        List = findViewById(R.id.List);
    }
}
