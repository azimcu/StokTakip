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

public class UrunGuncelleActivity extends AppCompatActivity {

    EditText stokKodu, urunIsim, stokMiktar, satisFiyat;
    Button urunGetir, urunGuncelle, geriDon;

    String old_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_guncelle);

        stokKodu = findViewById(R.id.stok_kodu);
        urunIsim = findViewById(R.id.urun_isim);
        stokMiktar = findViewById(R.id.stok_miktar);
        satisFiyat = findViewById(R.id.satis_fiyat);
        urunGetir = findViewById(R.id.urun_getir_button);
        urunGuncelle = findViewById(R.id.urun_guncelle_button);
        geriDon = findViewById(R.id.geri_button);

        urunGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stok_kodu;
                stok_kodu = stokKodu.getText().toString();

                getProductData(stok_kodu);
            }
        });

        urunGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stok_kodu,urun_isim,stok_miktar,urun_fiyat;
                stok_kodu = stokKodu.getText().toString();
                urun_isim = urunIsim.getText().toString();
                stok_miktar = stokMiktar.getText().toString();
                urun_fiyat = satisFiyat.getText().toString();

                getUpdateData(stok_kodu, urun_isim, stok_miktar, urun_fiyat, old_id);

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
    public void getProductData(String productid) {
        String url = "https://www.bestrp.net/getproduct.php";
        RequestQueue queue = Volley.newRequestQueue(UrunGuncelleActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);

                    String getstatus = respObj.getString("status");
                    if (getstatus.equals("true")) {
                        String getname = respObj.getString("product_name");
                        String getprice = respObj.getString("product_price");
                        String getstock = respObj.getString("product_stock");

                        old_id = productid;

                        urunIsim.setText(getname);
                        satisFiyat.setText(getprice);
                        stokMiktar.setText(getstock);
                    }else {
                        String geterrormessage = respObj.getString("message");
                        Toast.makeText(UrunGuncelleActivity.this, geterrormessage, Toast.LENGTH_SHORT).show();
                        urunIsim.setText("");
                        satisFiyat.setText("");
                        stokMiktar.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UrunGuncelleActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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
    private void getUpdateData(String productid, String productname, String productstock, String productprice, String productoldid) {
        String url = "https://www.bestrp.net/getgeneralupdate.php";
        RequestQueue queue = Volley.newRequestQueue(UrunGuncelleActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);


                    String getstatus = respObj.getString("status");
                    if (getstatus.equals("true")) {
                        String getmessage = respObj.getString("message");
                        Toast.makeText(UrunGuncelleActivity.this, getmessage, Toast.LENGTH_SHORT).show();
                    }else {
                        String getmessage = respObj.getString("message");
                        Toast.makeText(UrunGuncelleActivity.this, getmessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UrunGuncelleActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", productid);
                params.put("old_id", productoldid);
                params.put("product_name", productname);
                params.put("stock_count", productstock);
                params.put("product_price", productprice);
                return params;
            }
        };
        queue.add(request);
    }
}