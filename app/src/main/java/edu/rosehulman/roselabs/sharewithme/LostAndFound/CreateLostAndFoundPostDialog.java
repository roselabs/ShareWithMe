package edu.rosehulman.roselabs.sharewithme.LostAndFound;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.rosehulman.roselabs.sharewithme.Interfaces.CreateCallback;
import edu.rosehulman.roselabs.sharewithme.R;

/**
 * Created by josebaf on 2/7/2016.
 */
public class CreateLostAndFoundPostDialog extends DialogFragment {

    private LostAndFoundPost mPost;
    CreateCallback mCallback;
    private boolean mLostFoundFlag; // Not being used yet
    private EditText mPostTitle, mPostDescription, mPostKeywords;
    private RadioGroup mRadioGroup;

    public CreateLostAndFoundPostDialog() {
        //Empty
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null){
            mPost = getArguments().getParcelable("post");
        } else {
            mPost = null;
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mCallback = (CreateCallback) getActivity();
        View view = inflater.inflate(R.layout.fragment_create_lost_and_found, null);

        mRadioGroup = (RadioGroup)view.findViewById(R.id.lost_and_found_radio_group);
        mPostTitle = (EditText)view.findViewById(R.id.title_edit_text);
        mPostDescription = (EditText)view.findViewById(R.id.description_edit_text);
        mPostKeywords = (EditText)view.findViewById(R.id.keyword_edit_text);

        if (mPost != null){
            updateEditTexts();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a post in Lost and Found")
                .setView(view)
                .setPositiveButton(R.string.create_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LostAndFoundPost post = new LostAndFoundPost();
                        post.setTitle(mPostTitle.getText().toString());
                        post.setDescription(mPostDescription.getText().toString());
                        post.setLostFound(mRadioGroup.getCheckedRadioButtonId() == R.id.lost_radio_button);
                        mCallback.onCreatePostFinished(post);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(R.string.draft_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    private void updateEditTexts(){
        if (mPost.isLostFound())
            mRadioGroup.check(R.id.lost_radio_button);
        else
            mRadioGroup.check(R.id.found_radio_button);

        mPostTitle.setText(mPost.getTitle());
        mPostDescription.setText(mPost.getDescription());
        mPostKeywords.setText(mPost.getKeywords());
    }
}
