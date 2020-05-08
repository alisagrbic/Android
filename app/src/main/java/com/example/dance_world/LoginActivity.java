package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.User;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    Button SignUp, Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      /*  helper = DatabaseHelper.getInstance(this);
        User user = new User("Alisa", "Grbic", "Alisic", "alisa.grbic@gmail.com", "alisa123", 0);
        helper.UserDao().insertUser(user);*/

        SignUp = findViewById(R.id.SignUp);
        Login = findViewById(R.id.Login);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ArtistsActivity.class);
                startActivity(intent);
            }
        });
    }
}
