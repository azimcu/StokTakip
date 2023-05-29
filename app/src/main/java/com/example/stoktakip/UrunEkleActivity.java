package com.example.stoktakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UrunEkleActivity extends AppCompatActivity {

    EditText stokKodu, urunIsim, stokMiktar, satisFiyat;
    Button urunEkle, geriDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);

        stokKodu = findViewById(R.id.stok_kodu);
        urunIsim = findViewById(R.id.urun_isim);
        stokMiktar = findViewById(R.id.stok_miktar);
        satisFiyat = findViewById(R.id.satis_fiyat);
        urunEkle = findViewById(R.id.urun_ekle_button);
        geriDon = findViewById(R.id.geri_button);

        urunEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stok_kodu,urun_isim,stok_miktar,urun_fiyat;
                stok_kodu = stokKodu.getText().toString();
                urun_isim = urunIsim.getText().toString();
                stok_miktar = stokMiktar.getText().toString();
                urun_fiyat = satisFiyat.getText().toString();

                getInsertData(stok_kodu, urun_isim, stok_miktar, urun_fiyat);
            }
        });

        geriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuncelleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getInsertData(String productid,String productname,String stockcount, String productprice) {
        String url = "https://www.bestrp.net/getnewproduct.php";
        RequestQueue queue = Volley.newRequestQueue(UrunEkleActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);

                    String getstatus = respObj.getString("status");
                    String getessage = respObj.getString("message");
                    if (getstatus.equals("true")) {
                        Toast.makeText(UrunEkleActivity.this, getessage, Toast.LENGTH_SHORT).show();
                        urunIsim.setText("");
                        stokMiktar.setText("");
                        satisFiyat.setText("");
                        stokKodu.setText("");
                    }else {
                        Toast.makeText(UrunEkleActivity.this, getessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UrunEkleActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("stock_code", productid);
                params.put("product_name", productname);
                params.put("stock_count", stockcount);
                params.put("product_price", productprice);
                return params;
            }
        };
        queue.add(request);
    }
}