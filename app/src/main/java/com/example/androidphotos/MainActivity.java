package com.example.androidphotos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.androidphotos.Model.Album;
import com.example.androidphotos.Model.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AlbumDialogFragment.AlbumDialogListener {

    private static final String TAG = "MainActivity";

    //private ArrayList<Album> albums = new ArrayList<>();
    private AlbumRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String json = JsonHelper.fileToJson(this);
        if(!json.equals("")){
            UserData.setAlbums(JsonHelper.jsonToList(json));
        }else {
            UserData.setAlbums(new ArrayList<Album>());
        }

//        albums.add(new Album("TEST"));
        adapter = new AlbumRecyclerViewAdapter(UserData.albums, this);

        initRecyclerView();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                openDialog();
                adapter.notifyDataSetChanged();
                //adapter.notifyItemInserted(albums.size());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialog(){
        AlbumDialogFragment d = new AlbumDialogFragment();
        d.show(getSupportFragmentManager(), "EXAMPLE");
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: ");
        RecyclerView recyclerView = findViewById(R.id.albumList);
        //AlbumRecyclerViewAdapter adapter = new AlbumRecyclerViewAdapter(albums, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void getName(String name) {
        System.out.println(name);
        UserData.albums.add(new Album(name));
        String json = JsonHelper.listToJson(UserData.albums);
        JsonHelper.jsonToFile(json, this);
    }
}
