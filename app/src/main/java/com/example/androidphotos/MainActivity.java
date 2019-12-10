package com.example.androidphotos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.Album;
import com.example.androidphotos.Model.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AlbumDialogFragment.AlbumDialogListener {

    private static final String TAG = "MainActivity";

    //private ArrayList<Album> albums = new ArrayList<>();
    public AlbumRecyclerViewAdapter adapter;
    private Context context = this;

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

        FloatingActionButton searchFab = findViewById(R.id.fabSearch);
        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
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

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            UserData.albums.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };
}
