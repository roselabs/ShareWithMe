package edu.rosehulman.roselabs.sharewithme.Dashboard;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellDetailFragment;
import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellPost;
import edu.rosehulman.roselabs.sharewithme.Constants;
import edu.rosehulman.roselabs.sharewithme.Interfaces.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.LostAndFound.LostAndFoundDetailFragment;
import edu.rosehulman.roselabs.sharewithme.LostAndFound.LostAndFoundPost;
import edu.rosehulman.roselabs.sharewithme.R;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesDetailFragment;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesPost;
import edu.rosehulman.roselabs.sharewithme.Utils;

/**
 * Created by josebaf on 2/15/2016.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<DashboardPost> mDashboardPostList;
    private final OnListFragmentInteractionListener mListener;
    private Firebase mRidesRef;
    private Firebase mBuyAndSellRef;
    private Firebase mLostAndFoundRef;
    private Query mRidesQuery;
    private Query mBuyAndSellQuery;
    private Query mLostAndFoundQuery;
    private ChildEventListener mChildEventListener;

    private DashboardPost mDashboardPost;
    private RidesPost mRidesPost;
    private BuySellPost mBuyAndSellPost;
    private LostAndFoundPost mLostAndFoundPost;

    public DashboardAdapter(OnListFragmentInteractionListener listener) {
        this.mListener = listener;
        mDashboardPostList = new ArrayList<>();

        mChildEventListener = new DashboardEventListener();

        mRidesRef = new Firebase(Constants.FIREBASE_RIDES_POST_URL);
        mRidesQuery = mRidesRef.orderByChild("postDate").limitToFirst(20);
        mRidesQuery.addChildEventListener(mChildEventListener);

        mBuyAndSellRef = new Firebase(Constants.FIREBASE_BUY_SELL_POST_URL);
        mBuyAndSellQuery = mBuyAndSellRef.orderByChild("postDate").limitToFirst(20);
        mBuyAndSellQuery.addChildEventListener(mChildEventListener);

        mLostAndFoundRef = new Firebase(Constants.FIREBASE_LOST_AND_FOUND_POST_URL);
        mLostAndFoundQuery = mLostAndFoundRef.orderByChild("postDate").limitToFirst(20);
        mLostAndFoundQuery.addChildEventListener(mChildEventListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mDashboardPost = mDashboardPostList.get(position);

        holder.mTitleTextView.setText(mDashboardPost.getTitle());
        holder.mDescriptionTextView.setText(String.format("@%s at %s", mDashboardPost.getUserId(),
                Utils.getStringDate(mDashboardPost.getPostDate())));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: create a fragment for each category and sendFragmentToInflate
                switch (mDashboardPost.getCategory()){
                    case "Rides":
                        Query rides = mRidesRef.orderByChild("title").equalTo(mDashboardPost.getTitle());
                        rides.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mRidesPost = dataSnapshot.getValue(RidesPost.class);
                                mRidesPost.setKey(dataSnapshot.getKey());

                                if (!mRidesPost.getKey().equals(mDashboardPost.getKey()))
                                    return;

                                Fragment fragment = new RidesDetailFragment(mRidesPost);
                                mListener.sendFragmentToInflate(fragment);
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                //Empty
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                //Empty
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                //Empty
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                //Empty
                            }
                        });
                        break;
                    case "BuyAndSell":
                        Query buyAndSell = mBuyAndSellRef.orderByChild("title").equalTo(mDashboardPost.getTitle());
                        buyAndSell.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mBuyAndSellPost = dataSnapshot.getValue(BuySellPost.class);
                                mBuyAndSellPost.setKey(dataSnapshot.getKey());

                                if (!mBuyAndSellPost.getKey().equals(mDashboardPost.getKey()))
                                    return;

                                Fragment fragment = new BuySellDetailFragment(mBuyAndSellPost);
                                mListener.sendFragmentToInflate(fragment);
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                //Empty
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                //Empty
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                //Empty
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                //Empty
                            }
                        });
                        break;
                    case "LostAndFound":
                        Query lostAndFound = mLostAndFoundRef.orderByChild("title").equalTo(mDashboardPost.getTitle());
                        lostAndFound.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mLostAndFoundPost = dataSnapshot.getValue(LostAndFoundPost.class);
                                mLostAndFoundPost.setKey(dataSnapshot.getKey());

                                if(!mLostAndFoundPost.getKey().equals(mDashboardPost.getKey()))
                                    return;

                                Fragment fragment = new LostAndFoundDetailFragment(mLostAndFoundPost);
                                mListener.sendFragmentToInflate(fragment);
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                //Empty
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                //Empty
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                //Empty
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                //Empty
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDashboardPostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mTitleTextView;
        public TextView mDescriptionTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleTextView = (TextView) view.findViewById(R.id.post_title);
            mDescriptionTextView = (TextView) view.findViewById(R.id.post_description);
        }
    }

    private class DashboardEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            DashboardPost dashboardPost = new DashboardPost();

            String category = dataSnapshot.getRef().getParent().toString();
            category = category.substring(46);
            int slashIndex = category.indexOf("/");
            category = category.substring(0, slashIndex);

            switch (category){
                case "Rides":
                    RidesPost ridesPost = dataSnapshot.getValue(RidesPost.class);
                    ridesPost.setKey(dataSnapshot.getKey());
                    dashboardPost.setTitle(ridesPost.getTitle());
                    dashboardPost.setUserId(ridesPost.getUserId());
                    dashboardPost.setCategory(category);
                    dashboardPost.setKey(ridesPost.getKey());
                    dashboardPost.setPostDate(ridesPost.getPostDate());
                    break;
                case "BuyAndSell":
                    BuySellPost buySellPost = dataSnapshot.getValue(BuySellPost.class);
                    buySellPost.setKey(dataSnapshot.getKey());
                    dashboardPost.setTitle(buySellPost.getTitle());
                    dashboardPost.setUserId(buySellPost.getUserId());
                    dashboardPost.setCategory(category);
                    dashboardPost.setKey(buySellPost.getKey());
                    dashboardPost.setPostDate(buySellPost.getPostDate());
                    break;
                case "LostAndFound":
                    LostAndFoundPost lostAndFoundPost = dataSnapshot.getValue(LostAndFoundPost.class);
                    lostAndFoundPost.setKey(dataSnapshot.getKey());
                    dashboardPost.setTitle(lostAndFoundPost.getTitle());
                    dashboardPost.setUserId(lostAndFoundPost.getUserId());
                    dashboardPost.setCategory(category);
                    dashboardPost.setKey(lostAndFoundPost.getKey());
                    dashboardPost.setPostDate(lostAndFoundPost.getPostDate());
                    break;
                default:
                    break;
            }

            mDashboardPostList.add(0, dashboardPost);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //empty
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            //TODO
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //empty
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.d("Dashboard: ", firebaseError.getMessage());
        }
    }
}
