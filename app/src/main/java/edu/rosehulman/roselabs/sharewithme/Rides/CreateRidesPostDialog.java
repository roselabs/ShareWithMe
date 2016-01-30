package edu.rosehulman.roselabs.sharewithme.Rides;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.rosehulman.roselabs.sharewithme.Interfaces.CreateCallback;
import edu.rosehulman.roselabs.sharewithme.R;

/**
 * Created by rodrigr1 on 1/19/2016.
 */
public class CreateRidesPostDialog extends DialogFragment {

    CreateCallback mCallback;

    public CreateRidesPostDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mCallback = (CreateCallback) getActivity();
        View v = inflater.inflate(R.layout.fragment_create_rides, null);
        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.offer_radio_group);
        final EditText postPrice = (EditText) v.findViewById(R.id.price_edit_text);
        final EditText postTitle = (EditText) v.findViewById(R.id.title_edit_text);
        final EditText postDeparture = (EditText) v.findViewById(R.id.departure_edit_text);
        final EditText postRideDate = (EditText) v.findViewById(R.id.date_edit_text);
        final EditText postDestination = (EditText) v.findViewById(R.id.destination_edit_text);
        final EditText postDescription = (EditText) v.findViewById(R.id.description_edit_text);
        final EditText postKeywords = (EditText) v.findViewById(R.id.keyword_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a post in Rides")
                .setView(v)
                .setPositiveButton(R.string.create_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String price = postPrice.getText().toString();

                        price = formatPrice(price);

                        RidesPost post = new RidesPost((radioGroup.getCheckedRadioButtonId() == R.id.offer_radio_button),
                           price, postTitle.getText().toString(), postDeparture.getText().toString(),
                           postRideDate.getText().toString(), postDestination.getText().toString(), postDescription.getText().toString(),
                           postKeywords.getText().toString());
                        mCallback.onCreatePostFinished(post);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(R.string.draft_button_text, null);

        return builder.create();
    }

    private String formatPrice(String price){
        String formattedPrice = price;

        if(formattedPrice.contains(".")){
            int decimal = formattedPrice.indexOf(".");

            if(formattedPrice.length() -1 < decimal + 2){
                for(int i = formattedPrice.length() -1; i < decimal + 2; i++){
                    formattedPrice += 0;
                }
            }else if (formattedPrice.length() - 1 > decimal + 2) {
                formattedPrice = formattedPrice.substring(0, decimal + 3);
            }
            //else do nothing
        }else{
            formattedPrice += ".00";
        }

        return formattedPrice;
    }
}
