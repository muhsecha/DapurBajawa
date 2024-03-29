package com.example.dapurbajawa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dapurbajawa.Adapter.ViewAdapter;
import com.example.dapurbajawa.Model.HomeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewAdapter adapter;
    TextView tvJumlah;

    ArrayList<HomeModel> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        recyclerView = findViewById(R.id.list);

        datalist = new ArrayList<>();
        Log.d("geo", "onCreate: ");
        tvJumlah = findViewById(R.id.tvJumlah);

        AndroidNetworking.post(BaseUrl.url + "getnota.php")
                .addBodyParameter("statusOrder", "Tunggu")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("PAYLOAD");

                            for (int i = 0; i < data.length(); i++) {

                                HomeModel model = new HomeModel();
                                JSONObject object = data.getJSONObject(i);
                                model.setId(object.getString("id"));
                                model.setNama(object.getString("namaCustomer"));
                                model.setTanggal(object.getString("tanggalNota"));
                                model.setNohp(object.getString("nohpCustomer"));
                                model.setNote(object.getString("note"));
                                model.setKode(object.getString("id"));
                                model.setStatus(object.getString("statusOrder"));
                                datalist.add(model);

                            }

                            tvJumlah.setText(String.valueOf(data.length()));

                            adapter = new ViewAdapter(datalist);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewDataActivity.this);

                            recyclerView.setLayoutManager(layoutManager);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("geo", "onResponse: " + anError.toString());
                        Log.d("geo", "onResponse: " + anError.getErrorBody());
                        Log.d("geo", "onResponse: " + anError.getErrorCode());
                        Log.d("geo", "onResponse: " + anError.getErrorDetail());
                    }
                });


    }
}