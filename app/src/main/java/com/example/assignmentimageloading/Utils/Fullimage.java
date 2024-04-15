package com.example.assignmentimageloading.Utils;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.assignmentimageloading.R;

public class Fullimage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);

        ImageView imageView= findViewById(R.id.myZoomageView);
        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(imageView);

    }
}