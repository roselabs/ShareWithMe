package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

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

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuyAndSellFragment.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.Constants;
import edu.rosehulman.roselabs.sharewithme.R;

import java.util.ArrayList;
import java.util.List;

public class BuySellAdapter extends RecyclerView.Adapter<BuySellAdapter.ViewHolder> {

    private List<BuySellPost> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Firebase mRefFirebase;
    private ChildEventListener mChildEventListener;

    public BuySellAdapter(OnListFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
        mRefFirebase = new Firebase(Constants.FIREBASE_URL);
        mChildEventListener = new WeatherPicsChildEventListener();
        mRefFirebase.addChildEventListener(mChildEventListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BuySellPost post = mValues.get(position);
        holder.mTitleTextView.setText(post.getTitle());
        holder.mDescriptionTextView.setText(post.getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.sendAdapterToMain(holder.mPost);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setFilter(boolean buy){
        Query query;
        mRefFirebase.removeEventListener(mChildEventListener);
        if (buy)
            query = mRefFirebase.orderByChild("buy").equalTo(true);
        else
            query = mRefFirebase.orderByChild("buy").equalTo(false);
        mValues.clear();
        query.addChildEventListener(mChildEventListener);
    }

    public void add(BuySellPost post){
        mRefFirebase.push().setValue(post);
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

        @Override
        public String toString() {
            return super.toString() + "TODO";
        }
    }

    private class WeatherPicsChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            BuySellPost wp = dataSnapshot.getValue(BuySellPost.class);
            wp.setKey(dataSnapshot.getKey());
            mValues.add(0, wp);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //empty
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.e("MQ", firebaseError.getMessage());
        }
    }
}
