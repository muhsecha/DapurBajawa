package com.example.dapurbajawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dapurbajawa.Adapter.DetailAdapter;
import com.example.dapurbajawa.Adapter.ViewAdapter;
import com.example.dapurbajawa.Model.DetailModel;
import com.example.dapurbajawa.Model.HomeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DetailAdapter adapter;

    ArrayList<DetailModel> datalist;

    TextView tvname, tvNomer, tvCatatan;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

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

                            adapter = new DetailAdapter(datalist);

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


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(BaseUrl.url + "editnota.php")
                        .addBodyParameter("statusOrder", "selesai")
                        .addBodyParameter("id", id)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject PAYLOAD = response.getJSONObject("hasil");
                                    boolean sukses = PAYLOAD.getBoolean("respon");
                                    String roleuser = PAYLOAD.getString("roleuser");
                                    Log.d("PAYLOAD", "onResponse: " + PAYLOAD);
                                    if (sukses) {
                                        Toast.makeText(DetailDataActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DetailDataActivity.this, ViewDataActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(DetailDataActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

            }
        });

    }
}