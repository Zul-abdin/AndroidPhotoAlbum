package com.example.androidphotos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidphotos.Model.Tag;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TagRecyclerViewAdapter extends RecyclerView.Adapter<TagRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "TagRecyclerViewAdapter";

    private ArrayList<Tag> tags = new ArrayList<>();
    private Context mContext;

    public TagRecyclerViewAdapter(ArrayList<Tag> tags, Context mContext) {
        this.tags = tags;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.tagString.setText(tags.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tagString;
        RelativeLayout tagParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagString = itemView.findViewById(R.id.tagString);
            tagParent = itemView.findViewById(R.id.tagParent);
        }
    }

}
