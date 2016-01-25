package edu.rosehulman.roselabs.sharewithme;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by rodrigr1 on 1/19/2016.
 */
public class CreateRidesPostDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_create_rides, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a post in Rides")
                .setView(v)
                .setPositiveButton(R.string.create_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(R.string.draft_button_text, null);

        return builder.create();
    }
}
