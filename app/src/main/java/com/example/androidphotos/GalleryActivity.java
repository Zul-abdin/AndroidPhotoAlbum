package com.example.androidphotos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidphotos.Model.Photo;
import com.example.androidphotos.Model.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private ArrayList<Photo> photos = new ArrayList<>();
    private GalleryRecyclerViewAdapter adapter = new GalleryRecyclerViewAdapter(photos, this);

    Intent myFileIntent;
    Context context = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started.");
        getIncomingIntent();
        adapter = new GalleryRecyclerViewAdapter(photos, this);
        initRecyclerView();


        FloatingActionButton fab = findViewById(R.id.add_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added Image", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 1);
                adapter.notifyItemInserted(photos.size());
                String json = JsonHelper.listToJson(UserData.albums);
                JsonHelper.jsonToFile(json, context);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode==RESULT_OK && data != null){
            Uri path = data.getData();
            Log.d(TAG, "onActivityResult: " +  data.getData());
            Photo temp = new Photo(path);
            temp.caption = "Temp";
            photos.add(temp);
            Log.d(TAG, "onActivityResult: PhotoSIZE" + photos.size());
            adapter.notifyDataSetChanged();
        }
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIntent: checking for incoming intents.");
        if(getIntent().hasExtra("photos")){
            Log.d(TAG, "getIncomingIntent: found photos");
            photos = (ArrayList<Photo>) getIntent().getExtras().get("photos");
        }
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: ");
        RecyclerView recyclerView = findViewById(R.id.gallery_view);
        //AlbumRecyclerViewAdapter adapter = new AlbumRecyclerViewAdapter(albums, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
