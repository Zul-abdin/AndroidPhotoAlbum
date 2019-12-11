package com.example.androidphotos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidphotos.Model.Tag;

import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.tagString.setText(tags.get(position).toString());
        holder.tagParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Edit Tag");

                final EditText input = new EditText(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                View mview = inflater.inflate(R.layout.dialog_newtag, null);
                final Spinner key = mview.findViewById(R.id.tagSpinner);
                final EditText value = mview.findViewById(R.id.tagEdit);
                ArrayList<String> options = new ArrayList<>();
                options.add("person");
                options.add("location");
                Log.d(TAG, "onLongClick: " + options);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_spinner_item,
                        options);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Log.d(TAG, "onLongClick: " + key);
                key.setAdapter(adapter);

                builder.setView(mview)
                        .setTitle("Edit Tag")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String tagKey = key.getSelectedItem().toString();
                                String tagValue = value.getText().toString();
                                //listener.getTag(tagKey, tagValue);
                                tags.get(position).changeTag(tagKey, tagValue);
                                //tags.get(position).addTag(new Tag(tagKey, tagValue));
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tagString;
        LinearLayout tagParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagString = itemView.findViewById(R.id.tagString);
            tagParent = itemView.findViewById(R.id.tagItem);
        }
    }

}
