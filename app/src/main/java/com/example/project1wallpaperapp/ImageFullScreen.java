package com.example.project1wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageFullScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);


        String title =getIntent().getStringExtra("name");
        setTitle(title);
        ImageView imageView = findViewById(R.id.image);

        String url= getIntent().getStringExtra("image");

        Glide.with(this).load(url).into(imageView);


    }
}