package com.example.dapurbajawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dapurbajawa.Adapter.DetailAdapter;
import com.example.dapurbajawa.Model.DetailModel;
import com.example.dapurbajawa.Model.ProdukModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DetailAdapter adapter;

    ArrayList<DetailModel> datalist;
    private ArrayList mProdukList = new ArrayList<ProdukModel>();

    TextView tvname, tvNomer, tvCatatan;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        recyclerView = findViewById(R.id.rvDetail);
        tvname = findViewById(R.id.tvname);
        tvNomer = findViewById(R.id.tvNomer);
        tvCatatan = findViewById(R.id.tvCatatan);
        btnPost = findViewById(R.id.btnPost);

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String nama = extras.getString("nama");
        final String nohp = extras.getString("nohp");
        final String note = extras.getString("note");
        final String kode = extras.getString("kode");
        final String status = extras.getString("status");

        tvname.setText(nama);
        tvNomer.setText(nohp);
        tvCatatan.setText(note);
        datalist = new ArrayList<>();

        AndroidNetworking.post(BaseUrl.url + "getdetailnota.php")
                .addBodyParameter("kodeNota", kode)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("PAYLOAD");

                            for (int i = 0; i < data.length(); i++) {

                                DetailModel model = new DetailModel();
                                JSONObject object = data.getJSONObject(i);
                                model.setId(object.getString("id"));
                                model.setKodeMakanan(object.getString("kodeMakanan"));
                                model.setJumlah(object.getString("jumlahItem"));
                                datalist.add(model);

                            }

                            AndroidNetworking.post(BaseUrl.url + "getproduk.php")
                                    .setTag("test")
                                    .setPriority(Priority.LOW)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                JSONArray data = response.getJSONArray("PAYLOAD");
                                                for (int i = 0; i < data.length(); i++) {
                                                    ProdukModel model = new ProdukModel();
                                                    JSONObject object = data.getJSONObject(i);
                                                    model.setId(object.getString("id"));
                                                    model.setKodeMakanan(object.getString("kodeMakanan"));
                                                    model.setNamaMakanan(object.getString("namaMakanan"));
                                                    model.setJenisMakanan(object.getString("jenisMakanan"));
                                                    model.setHargaMakanan(object.getString("hargaMakanan"));
                                                    model.setAvatar(object.getString("avatar"));
                                                    mProdukList.add(model);
                                                }
                                                Log.e("model", "onResponse: " + mProdukList.size());

                                                adapter = new DetailAdapter(datalist, mProdukList);

                                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailDataActivity.this);

                                                recyclerView.setLayoutManager(layoutManager);

                                                recyclerView.setAdapter(adapter);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {

                                        }
                                    });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(BaseUrl.url + "editnota.php")
                        .addBodyParameter("statusOrder", "orderDiterima")
                        .addBodyParameter("id", id)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses){
                                        Intent intent = new Intent(DetailDataActivity.this, ViewDataActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(DetailDataActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(DetailDataActivity.this, "error", Toast.LENGTH_SHORT).show();

                                if (anError.getErrorCode() != 0) {
                                    Log.d("TAG", "onError errorCode : " + anError.getErrorCode());
                                    Log.d("TAG", "onError errorBody : " + anError.getErrorBody());
                                    Log.d("TAG", "onError errorDetail : " + anError.getErrorDetail());
                                } else {
                                    Log.d("TAG", "onError errorDetail : " + anError.getErrorDetail());
                                }
                            }
                        });

            }
        });

    }

    private void fetchDataProdukAPI() {



    }

}