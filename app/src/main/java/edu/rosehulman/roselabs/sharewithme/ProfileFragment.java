package edu.rosehulman.roselabs.sharewithme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import org.w3c.dom.Text;

import edu.rosehulman.roselabs.sharewithme.FormatData.FormatData;

public class ProfileFragment extends Fragment{

    private UserProfile mUserProfile;

    private ImageView mImageView;
    private TextView mProfileNameView;
    private TextView mProfileEmailView;
    private TextView mProfilePhoneView;

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
        //mProfileEmailView.setText(new Firebase(Constants.FIREBASE_URL).getAuth().getUid() + "@rose-hulman.edu");

        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
        Query queryRef = firebase.child("users").orderByChild("userID").equalTo(firebase.getAuth().getUid());
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

    class MyChildEventListener implements ChildEventListener{
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChild) {
            mUserProfile = snapshot.getValue(UserProfile.class);
            mUserProfile.setKey(snapshot.getKey());
            if(mUserProfile != null){
                String userName = "username: " + mUserProfile.getName();
                mProfileNameView.setText(userName);
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
    };
}
