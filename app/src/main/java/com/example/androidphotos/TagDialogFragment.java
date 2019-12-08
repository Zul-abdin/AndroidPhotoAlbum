package com.example.androidphotos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TagDialogFragment extends DialogFragment {

    private Spinner key;
    private EditText value;

    private ArrayList<String> options = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private TagDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_newtag, null);
        key = view.findViewById(R.id.tagSpinner);
        value = view.findViewById(R.id.tagEdit);
        Collections.addAll(options, getResources().getStringArray(R.array.tagKeyList));
        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,
                options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        key.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Add Tag")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String tagKey = key.getSelectedItem().toString();
                        String tagValue = value.getText().toString();
                        listener.getTag(tagKey, tagValue);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TagDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TagDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Implement TagDialogListener");
        }
    }

    public interface TagDialogListener{
        void getTag(String tagKey, String tagValue);
    }
}