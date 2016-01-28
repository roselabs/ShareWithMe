package edu.rosehulman.roselabs.sharewithme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
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

import com.firebase.client.Firebase;

import java.io.IOException;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuyAndSellFragment;
import edu.rosehulman.roselabs.sharewithme.BuyAndSell.CreateBuySellPostDialog;
import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellAdapter;
import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellPost;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BuyAndSellFragment.OnListFragmentInteractionListener, CreateBuySellPostDialog.CreateBuySellCallback {

    private BuySellAdapter mBuySellAdapter;
    private BuyAndSellFragment mBuyAndSellFragment;
    private ProfileFragment mProfileFragment;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
        if (firebase.getAuth() == null) {
            switchToLogin();
            return;
        }
        mProfileFragment = new ProfileFragment();
        mBuyAndSellFragment = new BuyAndSellFragment();
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

        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_picture_image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(mProfileFragment);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

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
            case R.id.menu_preferences:
                //TODO Implement: switchTo = new PreferencesFragment();
                break;
            case R.id.categories_lost_found:
                //TODO Implement: switchTo = new LostFoundFragment();
                break;
            case R.id.categories_events:
                //TODO Implement: switchTo = new EventsFragment();
                break;
            case R.id.categories_others:
                //TODO Implement: switchTo = new OthersFragment();
                break;
            case R.id.menu_help_and_feedback:
                switchTo = new HelpAndFeedbackFragment();
                break;
            case R.id.categories_rides:
                switchTo = new RidesFragment();
                break;
            case R.id.categories_buy:
                switchTo = mBuyAndSellFragment;
                break;
            default:
                break;
        }

        if (switchTo != null){
            switchToFragment(switchTo);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchToFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack("fragBack");
        ft.commit();
    }

    private void switchToLogin(){
        Firebase firebase = new Firebase(Constants.FIREBASE_URL);
        firebase.unauth();
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onLogout() {
        switchToLogin();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            Uri chosenImageUri = data.getData();

            Bitmap mBitmap = null;
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mProfileFragment.setImage(mBitmap);
            mImageView.setImageBitmap(mBitmap);
        }
    }

    @Override
    public void sendAdapterToMain(BuySellAdapter adapter) {
        mBuySellAdapter = adapter;
    }

    @Override
    public void onCreatePostFinished(BuySellPost post) {
        post.setUserId(new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        mBuySellAdapter.add(post);
    }
}
