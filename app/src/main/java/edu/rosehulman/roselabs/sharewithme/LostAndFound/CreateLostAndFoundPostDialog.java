package edu.rosehulman.roselabs.sharewithme.LostAndFound;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.rosehulman.roselabs.sharewithme.Interfaces.CreateCallback;
import edu.rosehulman.roselabs.sharewithme.R;

public class CreateLostAndFoundPostDialog extends DialogFragment {

    private LostAndFoundPost mPost;
    CreateCallback mCallback;
    private boolean mFlag;
    private EditText mPostTitle, mPostDescription, mPostKeywords;
    private RadioGroup mRadioGroup;

    public CreateLostAndFoundPostDialog() {
        //Empty
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPost = getArguments().getParcelable("post");
        } else {
            mPost = null;
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mCallback = (CreateCallback) getActivity();
        View view = inflater.inflate(R.layout.fragment_create_lost_and_found, null);

        mRadioGroup = (RadioGroup) view.findViewById(R.id.lost_and_found_radio_group);
        mPostTitle = (EditText) view.findViewById(R.id.title_edit_text);
        mPostDescription = (EditText) view.findViewById(R.id.description_edit_text);
        mPostKeywords = (EditText) view.findViewById(R.id.keyword_edit_text);

        if (mPost != null) {
            updateEditTexts();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a post in Lost and Found")
                .setView(view)
                .setPositiveButton(R.string.create_button_text, null)
                .setNegativeButton(android.R.string.cancel, null);
        if (mPost == null) {
            builder.setNeutralButton(R.string.draft_button_text, null);
        }

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog) getDialog();
        if(d != null){
            final Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlag = true;

                    checkEditText(mPostTitle, "Title is required!", 3);
                    checkEditText(mPostDescription, "Description is required!", 3);

                    if(mFlag){
                        LostAndFoundPost post = new LostAndFoundPost();
                        post.setTitle(mPostTitle.getText().toString());
                        post.setDescription(mPostDescription.getText().toString());
                        post.setLostFound(mRadioGroup.getCheckedRadioButtonId() == R.id.lost_radio_button);
                        post.setKeywords(mPostKeywords.getText().toString());

                        if (mPost != null) {
                            post.setKey(mPost.getKey());
                            post.setUserId(mPost.getUserId());
                        }

                        mCallback.onCreatePostFinished(post);

                        d.dismiss();
                    }
                }
            });

            Button draftButton = d.getButton(Dialog.BUTTON_NEUTRAL);
            draftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlag = true;

                    checkEditText(mPostTitle, "Title is required!", 3);
                    checkEditText(mPostDescription, "Description is required!", 3);

                    if (mFlag){
                        LostAndFoundPost post = new LostAndFoundPost();
                        post.setTitle(mPostTitle.getText().toString());
                        post.setDescription(mPostDescription.getText().toString());
                        post.setLostFound(mRadioGroup.getCheckedRadioButtonId() == R.id.lost_radio_button);

                        if (mPost != null) {
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

    private void checkEditText(EditText et, String message, int minChar) {
        if (et.getText().toString().length() < minChar) {
            et.setError(message);
            mFlag = false;
        }
    }


    private void updateEditTexts() {
        if (mPost.isLostFound())
            mRadioGroup.check(R.id.lost_radio_button);
        else
            mRadioGroup.check(R.id.found_radio_button);

        mPostTitle.setText(mPost.getTitle());
        mPostDescription.setText(mPost.getDescription());
        mPostKeywords.setText(mPost.getKeywords());
    }
}
