package com.example.mouraplacaslg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.FormBody;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OkHttpClient client = new OkHttpClient();

    private EditText editUsername;
    private EditText editPassword;
    private TextView textUsername;
    private TextView textPassword;
    private Button buttonEnter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.edit_login);
        editPassword = findViewById(R.id.edit_password);
        textUsername = findViewById(R.id.text_username);
        textPassword = findViewById(R.id.text_password);
        buttonEnter = findViewById(R.id.button_enter);

        buttonEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_enter) {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            if ("".equals(username) && "".equals(password)) {
                Toast.makeText(MainActivity.this, "Informe o login e senha", Toast.LENGTH_LONG).show();
            } else {
                login(username, password);
            }
        }

    }

    private void login(String username, String password) {
        String url = "http://es.eva.inf.br/eva/mobileWS/login";

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse;

                    try {
                        jsonResponse = new JSONObject(responseBody);
                        String storeId = jsonResponse.getString("moraPlacasId");

                        saveLoginData(username, storeId);
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_LONG).show());
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Falha ao realizar o login.", Toast.LENGTH_LONG).show());
                    }
                }
            }
        });

    }

    private void saveLoginData(String username, String mouraPlacasId) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("mouraPlacaId",mouraPlacasId);
        editor.apply();
    }


}
