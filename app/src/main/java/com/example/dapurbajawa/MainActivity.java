package com.example.dapurbajawa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dapurbajawa.Model.ProdukModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText  edUser,edpass;
    Button btnLogin;
    SharedPreferences sp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        edUser = findViewById(R.id.edUsername);
        edpass = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);

        sp = getSharedPreferences("login",MODE_PRIVATE);
        sp.edit().putString("logged", sp.getString("logged", "missing")).apply();

        String user = sp.getString("logged", "missing");

        if(user.equals("user")){
            Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edUser.getText().toString();
                String password = edpass.getText().toString().trim();
                progressDialog.setTitle("Logging In...");
                progressDialog.show();
                AndroidNetworking.post(BaseUrl.url + "login.php")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", password)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject PAYLOAD = response.getJSONObject("PAYLOAD");
                                    boolean sukses = PAYLOAD.getBoolean("respon");
                                    Log.d("PAYLOAD", "onResponse: " + PAYLOAD);
                                    if (sukses) {
                                        sp.edit().putString("logged","user").apply();
                                        Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                progressDialog.dismiss();
                                Log.d("TAG", "onError: " + anError.getErrorDetail());
                                Log.d("TAG", "onError: " + anError.getErrorBody());
                                Log.d("TAG", "onError: " + anError.getErrorCode());
                                Log.d("TAG", "onError: " + anError.getResponse());
                            }
                        });

            }
        });
    }

}