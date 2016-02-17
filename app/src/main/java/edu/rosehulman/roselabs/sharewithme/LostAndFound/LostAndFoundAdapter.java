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
import edu.rosehulman.roselabs.sharewithme.Rides.RidesPost;
import edu.rosehulman.roselabs.sharewithme.Utils;

public class LostAndFoundAdapter extends RecyclerView.Adapter<LostAndFoundAdapter.ViewHolder> {

    private List<LostAndFoundPost> mLostAndFoundList;
    private Firebase mLostAndFoundRef;
    private final OnListFragmentInteractionListener mListener;
    private ChildEventListener mChildEventListener;
    private int mToggleValue;

    public LostAndFoundAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
        mLostAndFoundList = new ArrayList<>();
        mToggleValue = 2;
        mLostAndFoundRef = new Firebase(Constants.FIREBASE_URL + "/categories/LostAndFound/posts");
        mChildEventListener = new LostAndFoundEventListener();
        mLostAndFoundRef.orderByChild("expirationDate").startAt(System.currentTimeMillis()).addChildEventListener(mChildEventListener);
    }

    public void update(LostAndFoundPost post) {
        mLostAndFoundRef.child(post.getKey()).setValue(post);
    }

    public void setFilterPost(int checkedId) {
        Query query;
        mLostAndFoundRef.removeEventListener(mChildEventListener);

        mToggleValue = checkedId;
        query = mLostAndFoundRef.orderByChild("expirationDate").startAt(System.currentTimeMillis());

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
        final LostAndFoundPost post = mLostAndFoundList.get(position);

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

        //Este codigo abaixo funciona mas nao achei funcional pro futuro, so para apagar mais facil nos testes
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String key = post.getKey();
                //TODO verify user permission
                mLostAndFoundRef.child(key).removeValue();
                return false;
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

        public void addPost(LostAndFoundPost lp){
            mLostAndFoundList.add(0, lp);
            notifyDataSetChanged();
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            LostAndFoundPost lp = dataSnapshot.getValue(LostAndFoundPost.class);
            lp.setKey(dataSnapshot.getKey());

            if(mToggleValue == 2){
                addPost(lp);
            }else{
                if(mToggleValue == 0){
                    if(lp.isLostFound()){
                        addPost(lp);
                    }
                }else{
                    if(!lp.isLostFound()){
                        addPost(lp);
                    }
                }
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //empty
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for (int i = 0; i < mLostAndFoundList.size(); i++) {
                if (mLostAndFoundList.get(i).getKey().equals(key)) {
                    mLostAndFoundList.remove(i);
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
            Log.d("LostAndFound: ", firebaseError.getMessage());
        }
    }
}
