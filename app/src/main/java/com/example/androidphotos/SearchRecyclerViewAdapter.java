package com.example.androidphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.Photo;

import java.util.ArrayList;


public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Photo> photos;
    private Context mContext;

    public SearchRecyclerViewAdapter(ArrayList<Photo> photos, Context mContext) {
        this.photos = photos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        SearchRecyclerViewAdapter.ViewHolder holder = new SearchRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageName.setText(photos.get(position).getCaption());
        holder.image.setImageURI(photos.get(position).getUrl());
        //holder.galleryParent.setLongClickable(true);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        LinearLayout galleryParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
            imageName = itemView.findViewById(R.id.photoName);
            galleryParent = itemView.findViewById(R.id.photoItem);


        }
    }
}
