package com.example.androidphotos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidphotos.Model.Album;
import com.example.androidphotos.Model.Photo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.Photo;
import com.example.androidphotos.Model.UserData;

import java.util.ArrayList;

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "GalleryRecyclerViewAdapter";

    private int albumPos;
    private ArrayList<Photo> photos;
    private Context mContext;

    public GalleryRecyclerViewAdapter(ArrayList<Photo> photos, int albumPos, Context mContext) {
        this.photos = photos;
        this.albumPos = albumPos;
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
        holder.galleryParent.setLongClickable(true);

        holder.galleryParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+ photos.get(position).getUrl());
                Toast.makeText(mContext, photos.get(position).getUrl().toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra("albumPos", albumPos);
                intent.putExtra("photoPos", position);
                mContext.startActivity(intent);
            }
        });

        holder.galleryParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Image Options");
                String[] options = mContext.getResources().getStringArray(R.array.imageOptions);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0: //move
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                                        inflate(R.layout.dialog_moveimage, null);
                                builder1.setTitle("Move Image");
                                final Spinner spinner = view.findViewById(R.id.moveImageSpinner);
                                ArrayAdapter<Album> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, UserData.albums);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);

                                builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        UserData.albums.get(spinner.getSelectedItemPosition()).addPhoto(photos.get(position));
                                        photos.remove(position);
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                });

                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder1.setView(view);
                                AlertDialog dialog1 = builder1.create();
                                dialog1.show();
                                break;
                            case 1: //delete
                                photos.remove(position);
                                notifyDataSetChanged();
                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
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
