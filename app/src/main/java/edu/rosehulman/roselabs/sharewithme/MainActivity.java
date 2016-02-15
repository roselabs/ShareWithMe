package edu.rosehulman.roselabs.sharewithme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.camera.CropImageIntentBuilder;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuyAndSellFragment;
import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellDetailFragment;
import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellPost;
import edu.rosehulman.roselabs.sharewithme.Dashboard.DashboardFragment;
import edu.rosehulman.roselabs.sharewithme.Drafts.DraftsBuySellAdapter;
import edu.rosehulman.roselabs.sharewithme.Drafts.DraftsFragment;
import edu.rosehulman.roselabs.sharewithme.Drafts.DraftsRidesAdapter;
import edu.rosehulman.roselabs.sharewithme.Interfaces.CreateCallback;
import edu.rosehulman.roselabs.sharewithme.Interfaces.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.LostAndFound.LostAndFoundDetailFragment;
import edu.rosehulman.roselabs.sharewithme.LostAndFound.LostAndFoundFragment;
import edu.rosehulman.roselabs.sharewithme.LostAndFound.LostAndFoundPost;
import edu.rosehulman.roselabs.sharewithme.Profile.ProfileFragment;
import edu.rosehulman.roselabs.sharewithme.Profile.UserProfile;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesDetailFragment;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesFragment;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesPost;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnListFragmentInteractionListener, CreateCallback {

    private DashboardFragment mDashboardFragment;
    private BuyAndSellFragment mBuyAndSellFragment;
    private RidesFragment mRidesFragment;
    private LostAndFoundFragment mLostAndFoundFragment;
    private ProfileFragment mProfileFragment;
    private ImageView mImageView;
    private UserProfile mUser;

    private TextView mProfileNameTextView;
    private DraftsBuySellAdapter mDraftsBuySellAdapter;
    private DraftsRidesAdapter mDraftsRidesAdapter;
    private DraftsFragment mDraftsFragment;

    private Firebase mFirebase;
    private Firebase mFirebaseRidePost;
    private Firebase mFirebaseRideDraft;
    private Firebase mFirebaseBuySellPost;
    private Firebase mFirebaseBuySellDraft;
    private Firebase mFirebaseLostAndFoundPost;
    private Firebase mFirebaseLostAndFoundDraft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase(Constants.FIREBASE_URL);
        if (mFirebase.getAuth() == null) {
            switchToLogin();
            return;
        }

        mFirebaseRidePost = new Firebase(Constants.FIREBASE_RIDES_POST_URL);
        mFirebaseRideDraft = new Firebase(Constants.FIREBASE_RIDES_DRAFT_URL);
        mFirebaseBuySellPost = new Firebase(Constants.FIREBASE_BUY_SELL_POST_URL);
        mFirebaseBuySellDraft = new Firebase(Constants.FIREBASE_BUY_SELL_DRAFT_URL);
        mFirebaseLostAndFoundPost = new Firebase(Constants.FIREBASE_LOST_AND_FOUND_POST_URL);
        mFirebaseLostAndFoundDraft = new Firebase(Constants.FIREBASE_LOST_AND_FOUND_DRAFT_URL);

        checkDeepLink();

        setupUser(mFirebase.getAuth().getUid());

        mDashboardFragment = new DashboardFragment();
        mDraftsFragment = new DraftsFragment();
        mBuyAndSellFragment = new BuyAndSellFragment();
        mRidesFragment = new RidesFragment();
        mLostAndFoundFragment = new LostAndFoundFragment();
        mProfileFragment = new ProfileFragment();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView emailView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email_text_view);
        emailView.setText(new Firebase(Constants.FIREBASE_URL).getAuth().getUid() + "@rose-hulman.edu");

        mProfileNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_name_header_text_view);

        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_picture_image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(mProfileFragment);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        switchToFragment(mDashboardFragment);
    }

    private void checkDeepLink(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || bundle.isEmpty()) return;
        final String category = bundle.getString("category");
        final String postKey = bundle.getString("postKey");
        if (postKey == null) return;
        Firebase firebase = null;
        switch(category){
            case "rides":
                firebase = mFirebaseRidePost;
                break;
            case "buyandsell":
                firebase = mFirebaseBuySellPost;
                break;
            case "lostandfound":
                firebase = mFirebaseLostAndFoundPost;
                break;
        }

        firebase.child(postKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Fragment fragment = null;
                switch(category){
                    case "rides":
                        RidesPost post = dataSnapshot.getValue(RidesPost.class);
                        post.setKey(dataSnapshot.getKey());
                        fragment = new RidesDetailFragment(post);
                        break;
                    case "buyandsell":
                        BuySellPost post2 = dataSnapshot.getValue(BuySellPost.class);
                        post2.setKey(dataSnapshot.getKey());
                        fragment = new BuySellDetailFragment(post2);
                        break;
                    case "lostandfound":
                        LostAndFoundPost post3 = dataSnapshot.getValue(LostAndFoundPost.class);
                        post3.setKey(dataSnapshot.getKey());
                        fragment = new LostAndFoundDetailFragment(post3);
                        break;
                }
                switchToFragment(fragment);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setupUser(String uid) {
        mUser = new UserProfile();
        mUser.setUserID(uid);

        MyChildEvent myChildEvent = new MyChildEvent();

        Query query = mFirebase.child("users").orderByChild("userID").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    if (mUser.getKey() == null)
                        mFirebase.child("users").push().setValue(mUser);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        query.addChildEventListener(myChildEvent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            onLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment switchTo = null;
        switch (item.getItemId()) {
            case R.id.menu_dashboard:
                switchTo = mDashboardFragment;
                break;
            case R.id.menu_drafts:
                switchTo = mDraftsFragment;
                break;
            case R.id.menu_preferences:
                //TODO Implement: switchTo = new PreferencesFragment();
                break;
            case R.id.menu_help_and_feedback:
                switchTo = new HelpAndFeedbackFragment();
                break;
            case R.id.categories_rides:
                switchTo = mRidesFragment;
                break;
            case R.id.categories_buy:
                switchTo = mBuyAndSellFragment;
                break;
            case R.id.categories_lost_found:
                switchTo = mLostAndFoundFragment;
                break;
            case R.id.categories_events:
                //TODO Implement: switchTo = new EventsFragment();
                break;
            case R.id.categories_others:
                //TODO Implement: switchTo = new OthersFragment();
                break;
            default:
                break;
        }

        if (switchTo != null) {
            switchToFragment(switchTo);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchToFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack("fragBack");
        ft.commit();
    }

    private void switchToLogin() {
        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
        firebase.unauth();
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onLogout() {
        Utils.disassociateUser();
        switchToLogin();
    }

    @Override
    public void sendFragmentToInflate(Fragment fragment) {
        switchToFragment(fragment);
    }

    @Override
    public void sendDialogFragmentToInflate(DialogFragment dialogFragment, String title) {
        dialogFragment.show(getSupportFragmentManager(), title);
    }

    @Override
    public void onCreatePostFinished(BuySellPost post) {
        if (post.getKey() != null) {
            mFirebaseBuySellDraft.child(post.getKey()).removeValue();
            mFirebaseBuySellPost.child(post.getKey()).removeValue();
        }

        post.setUserId(new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        mFirebaseBuySellPost.push().setValue(post);

        if (post.getKey() != null) {
            getSupportFragmentManager().popBackStack();
            switchToFragment(new BuySellDetailFragment(post));
        }
    }

    @Override
    public void onCreatePostFinished(RidesPost post) {
        if (post.getKey() != null) {
            mFirebaseRideDraft.child(post.getKey()).removeValue();
            mFirebaseRidePost.child(post.getKey()).removeValue();
        }

        post.setUserId(new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        mFirebaseRidePost.push().setValue(post);

        if (post.getKey() != null) {
            getSupportFragmentManager().popBackStack();
            switchToFragment(new RidesDetailFragment(post));
        }
    }

    @Override
    public void onCreatePostFinished(LostAndFoundPost post) {
        if (post.getKey() != null) {
            //mFirebaseLostAndFoundDraft.child(post.getKey()).removeValue();
            mFirebaseLostAndFoundPost.child(post.getKey()).removeValue();
        }

        post.setUserId(new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        mFirebaseLostAndFoundPost.push().setValue(post);

        if (post.getKey() != null) {
            getSupportFragmentManager().popBackStack();
            switchToFragment(new LostAndFoundDetailFragment(post));
        }
    }

    @Override
    public void onDraftPostFinished(RidesPost post) {
        if (post.getKey() != null)
            mFirebaseRideDraft.child(post.getKey()).removeValue();

        post.setUserId(new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        mFirebaseRideDraft.push().setValue(post);
    }

    @Override
    public void onDraftPostFinished(BuySellPost post) {
        if (post.getKey() != null)
            mFirebaseBuySellDraft.child(post.getKey()).removeValue();

        post.setUserId(new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        mFirebaseBuySellDraft.push().setValue(post);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        File croppedImageFile = new File(getFilesDir(), "profile_picture.jpg");

        if (requestCode == Constants.PICK_IMAGE_REQUEST) {
            Uri croppedImage = Uri.fromFile(croppedImageFile);
            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, croppedImage);
            cropImage.setOutlineColor(0xFF03A9F4);
            cropImage.setSourceImage(data.getData());

            Intent intent = cropImage.getIntent(this);
            if (Build.VERSION.SDK_INT > 19) {
                final int takeFlags = data.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                //noinspection ResourceType
                getContentResolver().takePersistableUriPermission(data.getData(), takeFlags);
            }

            startActivityForResult(intent, Constants.CROP_IMAGE_REQUEST);

        } else if (requestCode == Constants.CROP_IMAGE_REQUEST) {
            Bitmap bitmap = BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath());
            String str = Utils.encodeBitmap(bitmap);
            mUser.setPicture(str);
            Map<String, Object> userPicture = new HashMap<>();
            userPicture.put("picture", str);
            mFirebase.child("users").child(mUser.getKey()).updateChildren(userPicture);
        }
    }

    class MyChildEvent implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            UserProfile user = dataSnapshot.getValue(UserProfile.class);
            mUser.setKey(dataSnapshot.getKey());
            if (user.getPicture() != null) {
                mUser.setPicture(user.getPicture());
                mImageView.setImageBitmap(Utils.decodeStringToBitmap(mUser.getPicture()));
            }
            if (user.getName() != null) mProfileNameTextView.setText(user.getName());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            UserProfile user = dataSnapshot.getValue(UserProfile.class);
            mImageView.setImageBitmap(Utils.decodeStringToBitmap(mUser.getPicture()));
            if (user.getName() != null) mProfileNameTextView.setText(user.getName());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
