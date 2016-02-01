package edu.rosehulman.roselabs.sharewithme.Rides;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.rosehulman.roselabs.sharewithme.FormatData.FormatData;
import edu.rosehulman.roselabs.sharewithme.Interfaces.CreateCallback;
import edu.rosehulman.roselabs.sharewithme.R;

/**
 * Created by rodrigr1 on 1/19/2016.
 */
public class CreateRidesPostDialog extends DialogFragment {

    CreateCallback mCallback;
    private boolean flag;
    private RadioGroup radioGroup;
    private EditText postPrice, postTitle, postDeparture, postDestination, postDescription, postKeywords;
    private DatePicker postRideDate;

    public CreateRidesPostDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mCallback = (CreateCallback) getActivity();
        View v = inflater.inflate(R.layout.fragment_create_rides, null);
        radioGroup = (RadioGroup) v.findViewById(R.id.offer_radio_group);
        postPrice = (EditText) v.findViewById(R.id.price_edit_text);
        postTitle = (EditText) v.findViewById(R.id.title_edit_text);
        postDeparture = (EditText) v.findViewById(R.id.departure_edit_text);
        postRideDate = (DatePicker) v.findViewById(R.id.ride_date_picker);
        postDestination = (EditText) v.findViewById(R.id.destination_edit_text);
        postDescription = (EditText) v.findViewById(R.id.description_edit_text);
        postKeywords = (EditText) v.findViewById(R.id.keyword_edit_text);
//        final Button buttonDate = (Button) v.findViewById(R.id.button_date);

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

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    flag = true;

                    checkEditText(postTitle, "Title is required!", 3);
                    checkEditText(postDescription, "Description is required!", 3);

                    if(flag){
                        String price = FormatData.formatPrice(postPrice);

                        String date = FormatData.formatDateFromPicker(postRideDate);

                        RidesPost post = new RidesPost((radioGroup.getCheckedRadioButtonId() == R.id.offer_radio_button),
                                price, postTitle.getText().toString(), postDeparture.getText().toString(),
                                date, postDestination.getText().toString(), postDescription.getText().toString(),
                                postKeywords.getText().toString());
                        mCallback.onCreatePostFinished(post);
                        d.dismiss();
                    }
                }
            });
        }
    }

    private void checkEditText(EditText et, String message, int minChar){
        if (et.getText().toString().length() < minChar){
            et.setError(message);
            flag = false;
        }
    }
}