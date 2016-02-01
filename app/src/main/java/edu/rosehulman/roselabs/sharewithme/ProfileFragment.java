package edu.rosehulman.roselabs.sharewithme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import edu.rosehulman.roselabs.sharewithme.FormatData.FormatData;

public class ProfileFragment extends Fragment{

    private Firebase mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
    private UserProfile mUserProfile;

    private ImageView mImageView;
    private TextView mProfileNameView;
    private TextView mProfileEmailView;
    private TextView mProfilePhoneView;
    private Button mUpdateProfileButton;

    public ProfileFragment() {
        // Required empty constructor.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mImageView = (ImageView) view.findViewById(R.id.profile_image_view);
        mProfileNameView = (TextView) view.findViewById(R.id.name_text_view);
        mProfileEmailView = (TextView) view.findViewById(R.id.email_text_view);
        mProfilePhoneView = (TextView) view.findViewById(R.id.phone_text_view);

        mUpdateProfileButton = (Button) view.findViewById(R.id.update_profile_button);
        mUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateProfile();
            }
        });

        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        Query queryRef = mFirebaseRef.child("users").orderByChild("userID").equalTo(mFirebaseRef.getAuth().getUid());
        queryRef.addChildEventListener(new MyChildEventListener());

        if (getArguments() != null) {
            UserProfile user = getArguments().getParcelable("User");
            mImageView.setImageBitmap(MainActivity.decodeStringToBitmap(user.getPicture()));
        }

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        return view;
    }

    public void showUpdateProfile(){
        DialogFragment df = new DialogFragment(){
            @NonNull
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_update_profile_title);

                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_edit_profile, null, false);
                builder.setView(view);

                final EditText nameEditText = (EditText)view.findViewById(R.id.dialog_name_text);
                String name;
                if(mUserProfile.getName() == null || mUserProfile.getName().isEmpty()){
                    name = "";
                } else {
                    name = mUserProfile.getName();
                }
                nameEditText.setText(name);

                final EditText phoneEditText = (EditText)view.findViewById(R.id.dialog_phone_text);
                String phone;
                if(mUserProfile.getPhone() == null || mUserProfile.getPhone().isEmpty()){
                    phone = "";
                } else {
                    phone = mUserProfile.getPhone();
                }
                phoneEditText.setText(phone);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        mUserProfile.setName(name);

                        String phone = phoneEditText.getText().toString();
                        mUserProfile.setPhone(phone);

                        mFirebaseRef.child("users").child(mUserProfile.getKey()).setValue(mUserProfile);
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);

                return builder.create();
            }
        };

        df.show(getFragmentManager(), "update profile dialog");
    }

    class MyChildEventListener implements ChildEventListener{
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChild) {
            mUserProfile = snapshot.getValue(UserProfile.class);
            mUserProfile.setKey(snapshot.getKey());
            if(mUserProfile != null){
                if (mUserProfile.getName() != null) {
                    String userName = "name: " + mUserProfile.getName();
                    mProfileNameView.setText(userName);
                }
                String email = "email: " + mUserProfile.getUserID() + "@rose-hulman.edu";
                mProfileEmailView.setText(email);
                String phone = FormatData.formatPhoneNumber(mUserProfile.getPhone());
                mProfilePhoneView.setText(phone);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //Do nothing
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            //Do nothing
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //Do nothing
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            //Do nothing
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (getArguments() != null) {
            UserProfile user = getArguments().getParcelable("User");
            mImageView.setImageBitmap(MainActivity.decodeStringToBitmap(user.getPicture()));
        }
    }
}
