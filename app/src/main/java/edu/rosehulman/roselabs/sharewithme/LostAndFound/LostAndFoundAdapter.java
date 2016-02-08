package edu.rosehulman.roselabs.sharewithme.LostAndFound;

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

import edu.rosehulman.roselabs.sharewithme.Constants;
import edu.rosehulman.roselabs.sharewithme.Interfaces.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.R;
import edu.rosehulman.roselabs.sharewithme.Utils;

public class LostAndFoundAdapter extends RecyclerView.Adapter<LostAndFoundAdapter.ViewHolder> {

    private List<LostAndFoundPost> mLostAndFoundList;
    private Firebase mLostAndFoundRef;
    private final OnListFragmentInteractionListener mListener; // Not being used yet but will be.
    private ChildEventListener mChildEventListener;

    public LostAndFoundAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
        mLostAndFoundList = new ArrayList<>();
        mLostAndFoundRef = new Firebase(Constants.FIREBASE_URL + "/categories/LostAndFound/posts");
        mChildEventListener = new LostAndFoundEventListener();
        mLostAndFoundRef.addChildEventListener(mChildEventListener);
    }

    public void update(LostAndFoundPost post) {
        mLostAndFoundRef.child(post.getKey()).setValue(post);
    }

    public void setFilterPost(int checkedId) {
        Query query;
        mLostAndFoundRef.removeEventListener(mChildEventListener);

        if (checkedId < 1)
            query = mLostAndFoundRef.orderByChild("lostFound").equalTo(true);
        else if (checkedId < 2)
            query = mLostAndFoundRef.orderByChild("lostFound").equalTo(false);
        else
            query = mLostAndFoundRef.orderByChild("lostFound");

        mLostAndFoundList.clear();
        query.addChildEventListener(mChildEventListener);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LostAndFoundPost post = mLostAndFoundList.get(position);

        holder.mTitleTextView.setText(post.getTitle());
        holder.mDescriptionTextView.setText(String.format("@%s at %s", post.getUserId(),
                Utils.getStringDate(post.getPostDate())));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LostAndFoundDetailFragment(mLostAndFoundList.get(position));
                mListener.sendFragmentToInflate(fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLostAndFoundList.size();
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

    private class LostAndFoundEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            LostAndFoundPost post = dataSnapshot.getValue(LostAndFoundPost.class);
            post.setKey(dataSnapshot.getKey());
            mLostAndFoundList.add(0, post);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //empty
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for (LostAndFoundPost post : mLostAndFoundList) {
                if (post.getKey().equals(key)) {
                    mLostAndFoundList.remove(post);
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //empty
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.d("LostAndFound: ", firebaseError.getMessage());
        }
    }
}
