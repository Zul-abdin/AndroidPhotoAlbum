package com.example.androidphotos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class AlbumDialogFragment extends DialogFragment {
    private EditText name;
    private AlbumDialogListener listner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_newalbum, null);

        builder.setView(view)
                .setTitle("Add Album")
                .setPositiveButton("Make", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        String albumName = name.getText().toString();
                        listner.getName(albumName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlbumDialogFragment.this.getDialog().cancel();
                    }
                });

        name = view.findViewById(R.id.name);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (AlbumDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Implement AlbumDialogListener");
        }
    }

    public interface AlbumDialogListener{
        void getName(String name);
    }
}
