package com.example.androidphotos04;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Albums extends AppCompatActivity {

    private ListView listView;
    String[] albumNames;

    final static String ALBUM_NAME = "album_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_list);

        listView = findViewById(R.id.albums_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.album, albumNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                (p,v,pos,id) -> showAlbum(pos)
        );

    }

    private void showAlbum(int pos) {
        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_NAME, albumNames[pos]);
        Intent intent = new Intent(this, ShowAlbum.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
