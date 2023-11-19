package com.example.dansandroidtestangelaldapaulus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText username, password;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.buttonlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String usernamekey = username.getText().toString();
                String passwordkey = password.getText().toString();

                if (usernamekey.equals("angel") && passwordkey.equals("12345")){
                    //login berhasil
                    Toast.makeText(getApplicationContext(), "Login Sukses",
                            Toast.LENGTH_SHORT) .show();
                    Intent intent = new Intent(login.this,MainActivity.class);
                    login.this.startActivity(intent);
                    finish();
                }else{
                    //login gagal
                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                    builder.setMessage("Username atau Password Anda Salah!!!")
                            .setNegativeButton("Retry", null).create().show();
                }
            }
        });
    }
}