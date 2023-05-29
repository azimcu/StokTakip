package com.example.stoktakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    Button girisButton,kayitButton;
    EditText email,sifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        girisButton = findViewById(R.id.giris_button);
        kayitButton = findViewById(R.id.kayit_button);
        email = findViewById(R.id.email_tv);
        sifre = findViewById(R.id.sifre_tv);

        kayitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_giris,sifre_giris;
                email_giris = email.getText().toString();
                sifre_giris = sifre.getText().toString();

                if (TextUtils.isEmpty(email_giris)) {
                    Toast.makeText(LoginPage.this, "Eposta boş olamaz", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(sifre_giris)) {
                    Toast.makeText(LoginPage.this, "Şifre boş olamaz", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent intent = new Intent(getApplicationContext(), StokActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}