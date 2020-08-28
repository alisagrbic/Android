package com.example.dance_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.Notification;
import com.example.dance_world.database.entities.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    Button SignUp, Login;
    EditText Username, Password;
    CheckBox ShowPassword;
    String strings[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = DatabaseHelper.getInstance(this);


        SignUp = findViewById(R.id.SignUp);
        Login = findViewById(R.id.Login);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        ShowPassword = findViewById(R.id.ShowPassword);

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

                User user = helper.UserDao().getUserByUsername(Username.getText().toString());
                if(user != null) {
                    user.loggedIn=true;
                    helper.UserDao().updateUser(user);


                    Intent intent = new Intent(LoginActivity.this, MasterViewActivity.class);
                    ColorDrawable color = new ColorDrawable(Color.parseColor("#e60000"));
                    intent.putExtra("ApplyFestivalNames", strings);
                    intent.putExtra("colorTheme", "#e60000");
                    startActivity(intent);
                    intent.removeExtra("colorTheme");
                    intent.removeExtra("ApplyFestivalNames");
                }else {
                    Toast.makeText(LoginActivity.this, "You don't have an account. Please sign up.", Toast.LENGTH_LONG).show();
                }
            }
        });

        ShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }
}
