package com.pksoft.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pksoft.views.DrawView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ImageActivity extends AppCompatActivity {
    String path;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        Intent intent = getIntent();
        path = intent.getStringExtra("PATH");

        DrawView imageView=findViewById(R.id.imageView);
        imageView.setAreas(MainActivity.image.getRectangles());
        Bitmap bitmap= null;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);

        Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT);

    }

    public static Bitmap decodeSampledBitmapFromResource(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);

    }

}
