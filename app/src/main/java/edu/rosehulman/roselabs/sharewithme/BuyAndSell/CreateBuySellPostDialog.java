package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

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

import edu.rosehulman.roselabs.sharewithme.Interfaces.CreateCallback;
import edu.rosehulman.roselabs.sharewithme.R;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesPost;
import edu.rosehulman.roselabs.sharewithme.Utils;


public class CreateBuySellPostDialog extends DialogFragment {

    private BuySellPost mPost;
    private CreateCallback mCallback;
    private boolean mFlag;
    private RadioGroup mRadioGroup;
    private EditText mPostPrice, mPostTitle, mPostDescription, mPostKeywords;

    public CreateBuySellPostDialog(){

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
        View v = inflater.inflate(R.layout.fragment_create_buy_sell, null);
        mRadioGroup = (RadioGroup) v.findViewById(R.id.buy_sell_radio_group);
        mPostPrice = (EditText) v.findViewById(R.id.price_edit_text);
        mPostTitle = (EditText) v.findViewById(R.id.title_edit_text);
        mPostDescription = (EditText) v.findViewById(R.id.description_edit_text);
        mPostKeywords = (EditText) v.findViewById(R.id.keyword_edit_text);

        if (mPost != null){
            updateEditTexts();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a post in Buy/Sell")
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

                        BuySellPost post = new BuySellPost((mRadioGroup.getCheckedRadioButtonId() == R.id.buy_radio_button),
                                mPostTitle.getText().toString(), mPostDescription.getText().toString(),
                                mPostKeywords.getText().toString(), price);

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

                        BuySellPost post = new BuySellPost((mRadioGroup.getCheckedRadioButtonId() == R.id.buy_radio_button),
                                mPostTitle.getText().toString(), mPostDescription.getText().toString(),
                                mPostKeywords.getText().toString(), price);

                        if(mPost!= null){
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
        if (mPost.isBuy())
            mRadioGroup.check(R.id.buy_radio_button);
        else
            mRadioGroup.check(R.id.sell_radio_button);
        mPostPrice.setText(mPost.getPrice());
        mPostTitle.setText(mPost.getTitle());
        mPostDescription.setText(mPost.getDescription());
        mPostKeywords.setText(mPost.getKeywords());
    }
}
