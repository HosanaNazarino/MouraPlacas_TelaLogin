package com.example.mouraplacaslg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhotoActivity extends AppCompatActivity {

    private Button btnPhoto1;
    private Button btnPhoto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);

        initializeButtons();
        setButtonListeners();
    }

    private void initializeButtons() {
        btnPhoto1 = findViewById(R.id.btn_photo1);
        btnPhoto2 = findViewById(R.id.btn_photo2);
    }

    private void setButtonListeners() {
        btnPhoto1.setOnClickListener(v -> openCamera(1));
        btnPhoto2.setOnClickListener(v -> openCamera(2));
    }

    private void openCamera(int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Bitmap image = (Bitmap) data.getExtras().get("data");

            EventBus.getDefault().post(new PhotoEvent(image));
            finish();
        } else {
            Toast.makeText(this, "Erro ", Toast.LENGTH_SHORT).show();
        }
    }
}
