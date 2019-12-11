package com.example.androidphotos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.*;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    private EditText location;
    private EditText person;
    private Switch logic;
    private ArrayList<Photo> currentSearch;
    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        location = findViewById(R.id.locationSearch);
        person = findViewById(R.id.personSearch);
        logic = findViewById(R.id.andOrSwitch);
        currentSearch = new ArrayList<>();


        adapter = new SearchRecyclerViewAdapter(currentSearch, this);
        initRecyclerView();
        adapter.notifyDataSetChanged();

        logic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    logic.setText("AND");
                } else {
                    logic.setText("OR");
                }
            }
        });
    }

    public void search(View view) {
        String locationTag = location.getText().toString();
        String personTag = person.getText().toString();
        boolean checked = logic.isChecked();

        ArrayList<Photo> result = getPhotosFromUser();

        currentSearch.removeAll(currentSearch);

        if (locationTag.equals("") && personTag.equals("")){
            currentSearch.addAll(result);
            adapter.notifyDataSetChanged();
            Log.d(TAG, "search:" + result);
            return;
        }

        if (locationTag.equals("") || personTag.equals("")){
            if(locationTag.equals("")){
                Tag t = stringToTag(personTag, "person");
                result = getPhotosFromTag(result,t);
                Log.d(TAG, "search:" + result);

            }
            if(personTag.equals("")){
                Tag t = stringToTag(locationTag, "location");
                result = getPhotosFromTag(result,t);
                Log.d(TAG, "search:" + result);

            }
            currentSearch.addAll(result);
            adapter.notifyDataSetChanged();
            return;
        }

        if(checked) {
            Tag t = stringToTag(locationTag, "location");
            result = getPhotosFromTag(result,t);
            t = stringToTag(personTag, "person");
            result = getPhotosFromTag(result, t);
        } else {
            Tag t = stringToTag(locationTag, "location");
            ArrayList<Photo> temp = getPhotosFromTag(result, t);

            t = stringToTag(personTag, "person");
            result = getPhotosFromTag(result, t);
            result.addAll(temp);
        }
        Log.d(TAG, "search: ");
        currentSearch.addAll(result);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<Photo> getPhotosFromUser(){
        ArrayList<Photo> allPhotos = new ArrayList<>();

        for(Album currentAlbum : UserData.albums){
            allPhotos.addAll(currentAlbum.photos);
        }
        return allPhotos;
    }

    private ArrayList<Photo> getPhotosFromTag(ArrayList<Photo> photos, Tag tag){
        ArrayList<Photo> photosWithTag = new ArrayList<>();

        for(Photo photo : photos){
            for(Tag t : photo.getTags()){
                if(t.equals(tag)){
                    photosWithTag.add(photo);
                }
            }
        }

        return photosWithTag;
    }

    private Tag stringToTag(String s, String name){
        Tag t = new Tag(name, s);
        return t;
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
