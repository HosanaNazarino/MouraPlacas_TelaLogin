package com.example.mouraplacaslg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
    private ViewHolder mViewHolder = new ViewHolder();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.mViewHolder.editUsername = findViewById(R.id.edit_login);
        this.mViewHolder.editPassword = findViewById(R.id.edit_password);
        this.mViewHolder.textUsername = findViewById(R.id.text_username);
        this.mViewHolder.textPassword = findViewById(R.id.text_password);
        this.mViewHolder.buttonEnter = findViewById(R.id.button_enter);

        this.mViewHolder.buttonEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_enter) {
            String username = this.mViewHolder.editUsername.getText().toString();
            String password = this.mViewHolder.editPassword.getText().toString();
            if ("".equals(username) && "".equals(password)) {
                Toast.makeText(this, this.getString(R.string.informe_o_login_e_senha), Toast.LENGTH_LONG).show();
            }
        }
    }

    private static class ViewHolder {
        EditText editUsername;
        EditText editPassword;
        TextView textUsername;
        TextView textPassword;
        Button buttonEnter;

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
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Falha na conexão", Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String storeId = jsonResponse.getString("storeId");

                        saveLoginData(username, storeId);

                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show());
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao processar a resposta", Toast.LENGTH_LONG).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Falha no login", Toast.LENGTH_LONG).show());
                }
            }
        });
    }

    // Método para salvar os dados de login no SharedPreferences
    private void saveLoginData(String username, String storeId) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("storeId", storeId);
        editor.apply();
    }


}