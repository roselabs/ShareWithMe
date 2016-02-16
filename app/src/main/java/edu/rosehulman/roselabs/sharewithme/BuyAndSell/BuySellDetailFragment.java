package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.Date;

import edu.rosehulman.roselabs.sharewithme.Comments.Comment;
import edu.rosehulman.roselabs.sharewithme.Comments.CommentsAdapter;
import edu.rosehulman.roselabs.sharewithme.Constants;
import edu.rosehulman.roselabs.sharewithme.R;
import edu.rosehulman.roselabs.sharewithme.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuySellDetailFragment extends Fragment {

    private BuySellPost mPost;
    private CommentsAdapter mAdapter;

    public BuySellDetailFragment() {
        // Required empty public constructor
    }

    public BuySellDetailFragment(BuySellPost post) {
        mPost = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_sell_detail, container, false);

        TextView option = (TextView) view.findViewById(R.id.result_option_text_view);
        TextView price = (TextView) view.findViewById(R.id.result_price_text_view);
        TextView title = (TextView) view.findViewById(R.id.result_title_text_view);
        TextView description = (TextView) view.findViewById(R.id.result_description_text_view);
        TextView expiration = (TextView) view.findViewById(R.id.result_expiration_date_text_view);
        TextView keyword = (TextView) view.findViewById(R.id.result_keyword_text_view);
        TextView authorTextView = (TextView) view.findViewById(R.id.author_textView);
        TextView editButton = (TextView) view.findViewById(R.id.edit_ride_post_text_view);

        if (!mPost.getUserId().equalsIgnoreCase(new Firebase(Constants.FIREBASE_URL).getAuth().getUid()))
            hideView(editButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateBuySellPostDialog crpd = new CreateBuySellPostDialog();
                Bundle b = new Bundle();
                b.putParcelable("post", mPost);
                crpd.setArguments(b);
                crpd.show(getFragmentManager(), "Edit Post");
            }
        });
        authorTextView.setText(String.format("@%s at %s", mPost.getUserId(), Utils.getStringDate(mPost.getPostDate())));

        mAdapter = new CommentsAdapter("buyAndSell", mPost.getKey());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.comments_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), null));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);

        Button sendButton = (Button) view.findViewById(R.id.send_comment_button);
        final EditText commentEditText = (EditText) view.findViewById(R.id.comment_message_edit_text);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();
                if (!comment.isEmpty()) {
                    Date date = new Date();
                    Comment c = new Comment();
                    c.setContent(comment);
                    c.setPostKey(mPost.getKey());
                    c.setDate(date);
                    c.setUserId(new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
                    Utils.sendNotification(c.getUserId(), mPost.getUserId(), mPost.getKey(), "buyandsell");
                    mAdapter.add(c);
                    commentEditText.setText("");
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

        String optionValue;
        if (mPost.isBuy())
            optionValue = "Buy";
        else
            optionValue = "Sell";

        //Displays the values and hides the view if empty
        option.setText(optionValue);

        if (mPost.getPrice().isEmpty()) {
            price.setText("$ not informed");
        } else {
            price.setText("$ " + mPost.getPrice());
        }

        title.setText(mPost.getTitle());

        if (mPost.getDescription().isEmpty()) {
            hideView(description);
            hideView(view.findViewById(R.id.description_text_view));
        } else {
            description.setText(mPost.getDescription());
        }

        if (mPost.getExpirationDate() == null){
            expiration.setText("Expiration Date");
        } else {
            expiration.setText("Expires at " + Utils.getStringDate(mPost.getExpirationDate()));
        }

        if (mPost.getKeywords().isEmpty()) {
            hideView(keyword);
            hideView(view.findViewById(R.id.keyword_text_view));
        } else {
            keyword.setText(mPost.getKeywords());
        }

        return view;
    }

    private void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    public void setPost(BuySellPost post){
        mPost = post;
    }

}
