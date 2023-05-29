package com.example.stoktakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StokActivity extends AppCompatActivity {

    BottomNavigationView bottomNavi;
    EditText stokKodu,yeniStok;
    TextView urunIsim,urunMevcutStok;
    Button stokGetirButton,stokGuncelleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

        bottomNavi = findViewById(R.id.bottomNavi);
        stokKodu = findViewById(R.id.stok_kodu);
        urunIsim = findViewById(R.id.urun_isim_tv);
        urunMevcutStok = findViewById(R.id.urun_mevcut_stok);
        yeniStok = findViewById(R.id.stok_miktar_edittext);
        stokGetirButton = findViewById(R.id.stok_kodu_getir);
        stokGuncelleButton = findViewById(R.id.stok_guncelle);

        stokGetirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stok_kodu;
                stok_kodu = stokKodu.getText().toString();

                getProductData(stok_kodu);
            }
        });

        stokGuncelleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stok_kodu,yeni_stok;
                stok_kodu = stokKodu.getText().toString();
                yeni_stok = yeniStok.getText().toString();

                getUpdateData(stok_kodu, yeni_stok);
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

    private void getProductData(String productid) {
        String url = "https://www.bestrp.net/getproduct.php";
        RequestQueue queue = Volley.newRequestQueue(StokActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);

                    String getstatus = respObj.getString("status");
                    if (getstatus.equals("true")) {
                        String getproductname = respObj.getString("product_name");
                        String getstockcount = respObj.getString("product_stock");

                        urunIsim.setText(getproductname);
                        urunMevcutStok.setText(getstockcount);
                    }else {
                        String geterrormessage = respObj.getString("message");
                        Toast.makeText(StokActivity.this, geterrormessage, Toast.LENGTH_SHORT).show();
                        urunIsim.setText("");
                        urunMevcutStok.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StokActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", productid);
                return params;
            }
        };
        queue.add(request);
    }

    private void getUpdateData(String productid, String stockcount) {
        String url = "https://www.bestrp.net/getstockupdate.php";
        RequestQueue queue = Volley.newRequestQueue(StokActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);


                    String getstatus = respObj.getString("status");
                    if (getstatus.equals("true")) {
                        String getmessage = respObj.getString("message");
                        Toast.makeText(StokActivity.this, getmessage, Toast.LENGTH_SHORT).show();

                        urunMevcutStok.setText(stockcount);
                    }else {
                        String getmessage = respObj.getString("message");
                        Toast.makeText(StokActivity.this, getmessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StokActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", productid);
                params.put("stock", stockcount);
                return params;
            }
        };
        queue.add(request);
    }
}