package com.example.stoktakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class GuncelleActivity extends AppCompatActivity {

    BottomNavigationView bottomNavi;
    Button ekleButton, guncelleButton, silButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelle);

        bottomNavi = findViewById(R.id.bottomNavi);
        ekleButton = findViewById(R.id.ekle_button);
        guncelleButton = findViewById(R.id.guncelle_button);
        silButton = findViewById(R.id.sil_button);

        ekleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UrunEkleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        guncelleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UrunGuncelleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        silButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UrunSilActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int secim = item.getItemId();

                if (secim == R.id.urunler) {
                    secim = 1;
                }
                if (secim == R.id.stok) {
                    secim = 2;
                }
                if (secim == R.id.guncelleme) {
                    secim = 3;
                }

                switch (secim) {
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), UrunActivity.class);
                        startActivity(intent1);
                        finish();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new UrunFrag()).commit();
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), StokActivity.class);
                        startActivity(intent2);
                        finish();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new StokFrag()).commit();
                        break;
                }
                return true;
            }
        });
    }
}