package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuyAndSellFragment.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.R;

import java.util.List;

public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private List<BuySellPost> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPostRecyclerViewAdapter(OnListFragmentInteractionListener listener, List<BuySellPost> posts) {
        mValues = posts;
        mListener = listener;
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

    public void setmValues(List<BuySellPost> posts){
        mValues = posts;
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
}
