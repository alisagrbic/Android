package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.User;

public class RegisterActivity extends AppCompatActivity {

    EditText Name, Surname, Username, Email, Password;
    Button AddUserButton;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper = DatabaseHelper.getInstance(this);

        Name = findViewById(R.id.Name);
        Surname = findViewById(R.id.Surname);
        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        AddUserButton = findViewById(R.id.Login);

        AddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(Name.getText().toString(), Surname.getText().toString(), Username.getText().toString(),
                                    Email.getText().toString(), Password.getText().toString(), 0, true, "");
                helper.UserDao().insertUser(user);
                Toast.makeText(RegisterActivity.this, "You are now registered.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RegisterActivity.this, MasterViewActivity.class);
                startActivity(intent);
            }
        });

    }
}
