package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.rosehulman.roselabs.sharewithme.R;


public class CreateBuySellPostDialog extends DialogFragment {

    CreateBuySellCallback mCallback;

    public CreateBuySellPostDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mCallback = (CreateBuySellCallback) getActivity();
        View v = inflater.inflate(R.layout.fragment_create_buy_sell, null);
        final EditText postTitle = (EditText) v.findViewById(R.id.title_edit_text);
        final EditText postDescription = (EditText) v.findViewById(R.id.description_edit_text);
        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.buy_sell_radio_group);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a post in Buy/Sell")
                .setView(v)
                .setPositiveButton(R.string.create_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BuySellPost post = new BuySellPost(postTitle.getText().toString(), postDescription.getText().toString(), true);
                        if (radioGroup.getCheckedRadioButtonId() != R.id.buy_radio_button)
                            post.setBuy(false);
                        mCallback.onCreatePostFinished(post);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(R.string.draft_button_text, null);

        return builder.create();
    }

    public interface CreateBuySellCallback{
        void onCreatePostFinished(BuySellPost post);
    }
}
