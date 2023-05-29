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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UrunSilActivity extends AppCompatActivity {

    EditText stokKodu;
    TextView urunIsim, stokMiktar, satisFiyat;
    Button urunGetir, urunSil, geriDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_sil);

        stokKodu = findViewById(R.id.stok_kodu);
        urunIsim = findViewById(R.id.urun_isim);
        stokMiktar = findViewById(R.id.stok_miktar);
        satisFiyat = findViewById(R.id.satis_fiyat);
        urunGetir = findViewById(R.id.urun_getir_button);
        urunSil = findViewById(R.id.urun_sil_button);
        geriDon = findViewById(R.id.geri_button);

        urunGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stok_kodu;
                stok_kodu = stokKodu.getText().toString();

                getProductData(stok_kodu);
            }
        });

        urunSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stok_kodu;
                stok_kodu = stokKodu.getText().toString();

                getDeleteData(stok_kodu);
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

    private void getProductData(String productid) {
        String url = "https://www.bestrp.net/getproduct.php";
        RequestQueue queue = Volley.newRequestQueue(UrunSilActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);

                    String getstatus = respObj.getString("status");
                    if (getstatus.equals("true")) {
                        String getproductname = respObj.getString("product_name");
                        String getstockcount = respObj.getString("product_stock");
                        String getproductprice = respObj.getString("product_price");

                        urunIsim.setText(getproductname);
                        stokMiktar.setText(getstockcount);
                        satisFiyat.setText(getproductprice);
                    }else {
                        String geterrormessage = respObj.getString("message");
                        Toast.makeText(UrunSilActivity.this, geterrormessage, Toast.LENGTH_SHORT).show();
                        urunIsim.setText("");
                        stokMiktar.setText("");
                        satisFiyat.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UrunSilActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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

    private void getDeleteData(String productid) {
        String url = "https://www.bestrp.net/getdelete.php";
        RequestQueue queue = Volley.newRequestQueue(UrunSilActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);

                    String getstatus = respObj.getString("status");
                    String getmessage = respObj.getString("message");
                    if (getstatus.equals("true")) {
                        Toast.makeText(UrunSilActivity.this, getmessage, Toast.LENGTH_SHORT).show();
                        urunIsim.setText("");
                        stokMiktar.setText("");
                        satisFiyat.setText("");
                    }else {
                        Toast.makeText(UrunSilActivity.this, getmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UrunSilActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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
}