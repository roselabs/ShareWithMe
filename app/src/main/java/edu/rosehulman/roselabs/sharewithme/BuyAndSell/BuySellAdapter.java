package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

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

public class BuySellAdapter extends RecyclerView.Adapter<BuySellAdapter.ViewHolder> {

    private List<BuySellPost> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Firebase mRefFirebasePost;
    private ChildEventListener mChildEventListener;

    public BuySellAdapter(OnListFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
        mRefFirebasePost = new Firebase(Constants.FIREBASE_BUY_SELL_POST_URL);
        mChildEventListener = new BuySellChildEventListener();
        mRefFirebasePost.addChildEventListener(mChildEventListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final BuySellPost post = mValues.get(position);
        holder.mTitleTextView.setText(post.getTitle());
        holder.mDescriptionTextView.setText(String.format("@%s at %s", post.getUserId(), Utils.getStringDate(post.getPostDate())));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BuySellDetailFragment(mValues.get(position));
                mListener.sendFragmentToInflate(fragment);
            }
        });

        //Este codigo abaixo funciona mas nao achei funcional pro futuro, so para apagar mais facil nos testes
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String key = post.getKey();
                //TODO verify user permission
                mRefFirebasePost.child(key).removeValue();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setFilter(int value) {
        Query query;
        mRefFirebasePost.removeEventListener(mChildEventListener);

        if (value < 1)
            query = mRefFirebasePost.orderByChild("buy").equalTo(true);
        else if (value < 2)
            query = mRefFirebasePost.orderByChild("buy").equalTo(false);
        else
            query = mRefFirebasePost.orderByChild("buy");

        mValues.clear();
        query.addChildEventListener(mChildEventListener);
        notifyDataSetChanged();
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

    private class BuySellChildEventListener implements ChildEventListener {
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
            String key = dataSnapshot.getKey();
            for (int i = 0; i < mValues.size(); i++) {
                if (mValues.get(i).getKey().equals(key)) {
                    mValues.remove(i);
                    break;
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
            Log.e("MQ", firebaseError.getMessage());
        }
    }
}
