package com.example.androidphotos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.Album;

import java.util.ArrayList;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "AlbumRecyclerViewAdapter";

    private ArrayList<Album> albums = new ArrayList<>();
    private Context mContext;

    public AlbumRecyclerViewAdapter(ArrayList<Album> albums, Context mContext) {
        this.albums = albums;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.numberOfPhotos.setText(albums.get(position).photos.size()+"");
        holder.name.setText(albums.get(position).name);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+ albums.get(position).name);
                Toast.makeText(mContext, albums.get(position).name, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, GalleryActivity.class);
                intent.putExtra("photos", albums.get(position).photos);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView numberOfPhotos;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.albumName);
            numberOfPhotos = itemView.findViewById(R.id.numberPhotos);
            parentLayout = itemView.findViewById(R.id.parent_albumlayout);
        }
    }

    public interface OnAlbumListener{
        void onAlbumClick(int position);
    }
}
