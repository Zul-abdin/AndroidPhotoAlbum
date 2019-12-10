package com.example.androidphotos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androidphotos.Model.Tag;
import com.example.androidphotos.Model.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity implements TagDialogFragment.TagDialogListener {

    private static final String TAG = "PhotoActivity";
    private ArrayList<Tag> tags = new ArrayList<>();
    private int albumPos;
    private int photoPos;

    private TagRecyclerViewAdapter adapter = new TagRecyclerViewAdapter(tags, this);
    private ImageView photo;
    private TextView caption;

    Intent myFileIntent;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Log.d(TAG, "onCreate: started");
        getIncomingIntent();
        adapter = new TagRecyclerViewAdapter(tags, this);
        initRecyclerView();
        photo = findViewById(R.id.photo);
        photo.setImageURI(UserData.albums.get(albumPos).getPhotos().get(photoPos).getUrl());
        caption = findViewById(R.id.photoName);
        caption.setText(UserData.albums.get(albumPos).getPhotos().get(photoPos).getCaption());

        FloatingActionButton fab = findViewById(R.id.add_tag);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                openDialog();
                adapter.notifyDataSetChanged();
            }
        });

        Button slideshow = findViewById(R.id.buttonSlideshow);
        slideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SlideShowActivity.class);
                intent.putExtra("albumPos", albumPos);
                intent.putExtra("photoPos", photoPos);
                context.startActivity(intent);
            }
        });
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIntent: checking for incoming intents.");
        if(getIntent().hasExtra("albumPos") && getIntent().hasExtra("photoPos")){
            Log.d(TAG, "getIncomingIntent: found positions");
            albumPos = (int) getIntent().getExtras().get("albumPos");
            photoPos = (int) getIntent().getExtras().get("photoPos");
            tags = UserData.albums.get(albumPos).getPhotos().get(photoPos).getTags();
            Log.d(TAG, "getIncomingIntent: " + tags.size());
        }
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: ");
        RecyclerView recyclerView = findViewById(R.id.tag_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openDialog(){
        TagDialogFragment d = new TagDialogFragment();
        d.show(getSupportFragmentManager(), "EXAMPLE");
    }

    @Override
    public void getTag(String tagKey, String tagValue) {
        UserData.albums.get(albumPos).getPhotos().get(photoPos).addTag(new Tag(tagKey, tagValue));
    }
}
