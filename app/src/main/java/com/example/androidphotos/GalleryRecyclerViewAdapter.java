package com.example.androidphotos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.Photo;

import java.util.ArrayList;

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "GalleryRecyclerViewAdapter";

    private ArrayList<Photo> photos = new ArrayList<>();
    private Context mContext;

    public GalleryRecyclerViewAdapter(ArrayList<Photo> photos, Context mContext) {
        this.photos = photos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called" + photos.get(position).getUrl());
        holder.imageName.setText(photos.get(position).getCaption());
        //holder.image.setImageBitmap(BitmapFactory.decodeFile(photos.get(position).getUrl()));
        //File temp = new File(photos.get(position).getUrl());
        holder.image.setImageURI(photos.get(position).getUrl());

        holder.galleryParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+ photos.get(position).getUrl());
                Toast.makeText(mContext, photos.get(position).getUrl().toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra("albumPos", GalleryActivity.albumPos);
                intent.putExtra("photoPos", position);
                mContext.startActivity(intent);
            }
        });

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
