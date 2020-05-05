package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.User;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    Button SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = DatabaseHelper.getInstance(this);
        User user = new User("Alisa", "Grbic", "Alisic", "alisa.grbic@gmail.com", "alisa123", 0);
        helper.UserDao().insertUser(user);

        SignUp = findViewById(R.id.SignUp);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
