package com.example.androidphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.androidphotos.Model.Album;
import com.example.androidphotos.Model.UserData;

public class SlideShowActivity extends AppCompatActivity {

    private static final String TAG = "SlideShowActivity";

    private Album album;
    private int photoPos;

    private ImageView image;
    private Button next;
    private Button prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        Log.d(TAG, "onCreate: slideshow");
        getIncomingIntent();

        image = findViewById(R.id.currentImage);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        image.setImageURI(album.photos.get(photoPos).getUrl());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoPos == album.photos.size() - 1){
                    photoPos = -1;
                }
                image.setImageURI(album.photos.get(++photoPos).getUrl());
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoPos == 0){
                    photoPos = album.photos.size();
                }
                image.setImageURI(album.photos.get(--photoPos).getUrl());
            }
        });
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIntent: checking for incoming intents.");
        if(getIntent().hasExtra("albumPos") && getIntent().hasExtra("photoPos")){
            Log.d(TAG, "getIncomingIntent: found positions");
            album = UserData.albums.get((int) getIntent().getExtras().get("albumPos"));
            photoPos = (int) getIntent().getExtras().get("photoPos");
            Log.d(TAG, "getIncomingIntent: ");
        }
    }
}
