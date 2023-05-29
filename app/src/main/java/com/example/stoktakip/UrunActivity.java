package com.example.stoktakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UrunActivity extends AppCompatActivity {

    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun);

        bottomNavi = findViewById(R.id.bottomNavi);

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
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), StokActivity.class);
                        startActivity(intent2);
                        finish();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new StokFrag()).commit();
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), GuncelleActivity.class);
                        startActivity(intent3);
                        finish();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new GuncelleFrag()).commit();
                        break;
                }
                return true;
            }
        });
    }
}