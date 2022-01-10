package com.pksoft.activities;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.pksoft.util.Image;
import com.pksoft.util.Mask;
import com.pksoft.util.Rectangle;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


public class MainActivity extends AppCompatActivity {
    ImageCapture imageCapture;
    public static Image image;

    public MainActivity() {
    }

    public static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PkSoftApp");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("PkSoftApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        requestPermissions(new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        startCamera();


        ((Button) findViewById(R.id.button_capture)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        findViewById((R.id.button_segmentation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.process();
                /*Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                intent.putExtra("PATH", path);
                startActivity(intent);*/
            }
        });
    }

    protected void startCamera() {
        ListenableFuture<ProcessCameraProvider> p = ProcessCameraProvider.getInstance(this);
        p.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = p.get();
                    Preview.Builder b = new Preview.Builder();
                    Preview p = b.build();
                    p.setSurfaceProvider(((PreviewView) findViewById(R.id.viewFinder)).getSurfaceProvider());
                    imageCapture = (new ImageCapture.Builder()).build();
                    cameraProvider.unbindAll();
                    CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                    cameraProvider.bindToLifecycle(MainActivity.this, cameraSelector, p, imageCapture);
                } catch (ExecutionException e) {
                    Log.e("ERROR", e.getMessage());
                } catch (InterruptedException e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    protected void takePhoto() {
        if (imageCapture == null)
            Toast.makeText(this, "Camera not ready yet", Toast.LENGTH_LONG);

        File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
        ImageCapture.OutputFileOptions outputFileOptions = (new ImageCapture.OutputFileOptions.Builder(file)).build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull @NotNull ImageCapture.OutputFileResults outputFileResults) {
                try {
                    image=new Image(outputFileResults.getSavedUri());
                } catch (Exception e) {
                    Log.e("FILE NOT FOUND",e.getMessage());
                }
                Toast.makeText(MainActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull @NotNull ImageCaptureException exception) {
                Toast.makeText(MainActivity.this, "Error Image NOT saved: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}