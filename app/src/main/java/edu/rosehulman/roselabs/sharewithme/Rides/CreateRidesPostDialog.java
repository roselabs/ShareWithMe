package edu.rosehulman.roselabs.sharewithme.Rides;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

import edu.rosehulman.roselabs.sharewithme.Utils;
import edu.rosehulman.roselabs.sharewithme.Interfaces.CreateCallback;
import edu.rosehulman.roselabs.sharewithme.R;

/**
 * Created by rodrigr1 on 1/19/2016.
 */
public class CreateRidesPostDialog extends DialogFragment {

    private RidesPost mPost;
    private CreateCallback mCallback;
    private boolean mFlag;
    private RadioGroup mRadioGroup;
    private EditText mPostPrice, mPostTitle, mPostDeparture, mPostDestination, mPostDescription, mPostKeywords;
    private DatePicker mPostRideDate;

    public CreateRidesPostDialog(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        if (getArguments() != null){
            mPost = getArguments().getParcelable("post");
        } else {
            mPost = null;
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mCallback = (CreateCallback) getActivity();
        View v = inflater.inflate(R.layout.fragment_create_rides, null);
        mRadioGroup = (RadioGroup) v.findViewById(R.id.offer_radio_group);
        mPostPrice = (EditText) v.findViewById(R.id.price_edit_text);
        mPostTitle = (EditText) v.findViewById(R.id.title_edit_text);
        mPostDeparture = (EditText) v.findViewById(R.id.departure_edit_text);
        mPostRideDate = (DatePicker) v.findViewById(R.id.ride_date_picker);
        mPostDestination = (EditText) v.findViewById(R.id.destination_edit_text);
        mPostDescription = (EditText) v.findViewById(R.id.description_edit_text);
        mPostKeywords = (EditText) v.findViewById(R.id.keyword_edit_text);

        if (mPost != null){
            updateEditTexts();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a post in Rides")
                .setView(v)
                .setPositiveButton(R.string.create_button_text, null)
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
                    mFlag = true;

                    checkEditText(mPostTitle, "Title is required!", 3);
                    checkEditText(mPostDescription, "Description is required!", 3);

                    if(mFlag){
                        String price = Utils.formatPrice(mPostPrice);

                        Calendar c = Calendar.getInstance();
                        c.set(mPostRideDate.getYear(), mPostRideDate.getMonth(), mPostRideDate.getDayOfMonth());

                        RidesPost post = new RidesPost((mRadioGroup.getCheckedRadioButtonId() == R.id.offer_radio_button),
                                price, mPostTitle.getText().toString(), mPostDeparture.getText().toString(),
                                c.getTime(), mPostDestination.getText().toString(), mPostDescription.getText().toString(),
                                mPostKeywords.getText().toString());

                        if(mPost!= null){
                            post.setKey(mPost.getKey());
                            post.setUserId(mPost.getUserId());
                        }

                        mCallback.onCreatePostFinished(post);

                        d.dismiss();
                    }
                }
            });

            //Button Draft Under Construction
            Button draftButton = d.getButton(Dialog.BUTTON_NEUTRAL);
            draftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlag = true;

                    checkEditText(mPostTitle, "Title is required!", 3);
                    checkEditText(mPostDescription, "Description is required!", 3);

                    if(mFlag){
                        String price = Utils.formatPrice(mPostPrice);

                        Calendar c = Calendar.getInstance();
                        c.set(mPostRideDate.getYear(), mPostRideDate.getMonth(), mPostRideDate.getDayOfMonth());

                        RidesPost post = new RidesPost((mRadioGroup.getCheckedRadioButtonId() == R.id.offer_radio_button),
                                price, mPostTitle.getText().toString(), mPostDeparture.getText().toString(),
                                c.getTime(), mPostDestination.getText().toString(), mPostDescription.getText().toString(),
                                mPostKeywords.getText().toString());

                        if (mPost != null){
                            post.setKey(mPost.getKey());
                            post.setUserId(mPost.getUserId());
                        }
                        mCallback.onDraftPostFinished(post);

                        d.dismiss();
                    }
                }
            });

        }
    }

    private void checkEditText(EditText et, String message, int minChar){
        if (et.getText().toString().length() < minChar){
            et.setError(message);
            mFlag = false;
        }
    }

    private void updateEditTexts(){
        if (mPost.isOffer())
            mRadioGroup.check(R.id.offer_radio_button);
        else
            mRadioGroup.check(R.id.request_radio_button);
        Calendar cal = Calendar.getInstance();
        cal.setTime(mPost.getRideDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mPostPrice.setText(mPost.getPrice());
        mPostTitle.setText(mPost.getTitle());
        mPostDescription.setText(mPost.getDescription());
        mPostDeparture.setText(mPost.getDepartureLocal());
        mPostRideDate.updateDate(year, month, day);
        mPostDestination.setText(mPost.getDestinationLocal());
        mPostKeywords.setText(mPost.getKeywords());
    }
}
