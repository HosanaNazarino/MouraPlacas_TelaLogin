package com.example.mouraplacaslg;

import android.graphics.Bitmap;

public class PhotoEvent {
    private Bitmap image;

    public PhotoEvent(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}