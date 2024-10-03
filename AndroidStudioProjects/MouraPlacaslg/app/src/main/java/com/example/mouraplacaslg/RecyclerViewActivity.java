package com.example.mouraplacaslg;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OkHttpClient client = new OkHttpClient();
    private List<String> platesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getPlates();
    }

    private void getPlates() {
        String mouraPlacasId = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("mouraPlacas", "");
        String url = "http://es.eva.inf.br/eva/mobileWS/getAtendimentosApp?lojald=" + mouraPlacasId + "&version=2.84";

        client.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException) {
                showError("Ocorreu um problema na busca das placas.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    parseResponse(response.body().string());
                }
            }
        });
    }

    private void parseResponse(String responseBody) {
        try {
            JSONArray platesArray = new JSONObject(responseBody).getJSONArray("plates");
            for (int i = 0; i < platesArray.length(); i++) {
                platesList.add(platesArray.getString(i));
            }
            updateRecyclerView();
        } catch (Exception e) {
            showError("Problema ao processar as informações. ");
        }
    }

    private void updateRecyclerView() {
        runOnUiThread(() -> recyclerView.setAdapter(new PlateAdapter(platesList)));
    }

    private void showError(String message) {
        runOnUiThread(() -> Toast.makeText(RecyclerViewActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoEvent(PhotoEvent event) {
        Bitmap capturedImage = event.getImage();
    }
}

