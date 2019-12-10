package com.example.androidphotos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.Photo;
import com.example.androidphotos.Model.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private ArrayList<Photo> photos = new ArrayList<>();
    public int albumPos;
    private GalleryRecyclerViewAdapter adapter;

    Intent myFileIntent;
    Context context = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started.");
        getIncomingIntent();
        adapter = new GalleryRecyclerViewAdapter(photos, albumPos, this);
        initRecyclerView();


        FloatingActionButton fab = findViewById(R.id.add_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added Image", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                myFileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 1);
                //adapter.notifyItemInserted(photos.size());
                adapter.notifyDataSetChanged();
                String json = JsonHelper.listToJson(UserData.albums);
                JsonHelper.jsonToFile(json, context);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        String json = JsonHelper.listToJson(UserData.albums);
        JsonHelper.jsonToFile(json, context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String json = JsonHelper.listToJson(UserData.albums);
        JsonHelper.jsonToFile(json, context);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String json = JsonHelper.listToJson(UserData.albums);
        JsonHelper.jsonToFile(json, context);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode==RESULT_OK && data != null){
            Uri path = data.getData();
            Log.d(TAG, "onActivityResult: " +  data.getData());
            Photo temp = new Photo(path.toString());
            File file = new File(path.getPath());
            temp.caption = file.getName();
            photos.add(temp);
            Log.d(TAG, "onActivityResult: PhotoSIZE" + photos.size());
            adapter.notifyDataSetChanged();
        }
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIntent: checking for incoming intents.");
        if(getIntent().hasExtra("photos")){
            Log.d(TAG, "getIncomingIntent: " + photos.size());
            Log.d(TAG, "getIncomingIntent: found photos");
            int position = (int) getIntent().getExtras().get("photos");
            photos = UserData.albums.get(position).getPhotos();
            albumPos = position;
            Log.d(TAG, "getIncomingIntent: " + photos.size());
        }
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: ");
        RecyclerView recyclerView = findViewById(R.id.gallery_view);
        //AlbumRecyclerViewAdapter adapter = new AlbumRecyclerViewAdapter(albums, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            photos.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };
}
