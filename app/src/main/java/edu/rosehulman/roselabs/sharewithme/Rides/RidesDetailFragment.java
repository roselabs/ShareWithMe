package edu.rosehulman.roselabs.sharewithme.Rides;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.rosehulman.roselabs.sharewithme.Comments.Comment;
import edu.rosehulman.roselabs.sharewithme.Comments.CommentsAdapter;
import edu.rosehulman.roselabs.sharewithme.FormatData.FormatData;
import edu.rosehulman.roselabs.sharewithme.Interfaces.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RidesDetailFragment extends Fragment {

    private RidesPost mPost;
    private CommentsAdapter mAdapter;

    public RidesDetailFragment() {
        // Required empty public constructor
    }

    public RidesDetailFragment(RidesPost post){
        mPost = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rides_detail, container, false);

        TextView option = (TextView) view.findViewById(R.id.result_option_text_view);
        TextView price = (TextView) view.findViewById(R.id.result_price_text_view);
        TextView title = (TextView) view.findViewById(R.id.result_title_text_view);
        TextView departure = (TextView) view.findViewById(R.id.result_departure_text_view);
        TextView date = (TextView) view.findViewById(R.id.result_date_text_view);
        TextView destination = (TextView) view.findViewById(R.id.result_destination_text_view);
        TextView description = (TextView) view.findViewById(R.id.result_description_text_view);
        TextView expiration = (TextView) view.findViewById(R.id.result_expiration_date_text_view);
        TextView keyword = (TextView) view.findViewById(R.id.result_keyword_text_view);
        TextView authorTextView = (TextView) view.findViewById(R.id.author_textView);
        authorTextView.setText(String.format("@%s at %s", mPost.getUserId(), FormatData.getStringDate(mPost.getPostDate())));

        mAdapter = new CommentsAdapter("rides", mPost.getKey());

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
                if (!comment.isEmpty()){
                    Date date = new Date();
                    Comment c = new Comment();
                    c.setContent(comment);
                    c.setPostKey(mPost.getKey());
                    c.setDate(date);
                    mAdapter.add(c);
                    commentEditText.setText("");
                    //TODO hide device keyboard here
                }
            }
        });

        String optionValue;
        if(mPost.isOffer())
            optionValue = "Offer";
        else
            optionValue = "Request";

        //Displays the values and hides the view if empty
        option.setText(optionValue);

        if(mPost.getPrice().isEmpty()){
            price.setText("$ not informed");
        }else{
            price.setText("$ " + mPost.getPrice());
        }

        title.setText(mPost.getTitle());

        if(mPost.getDepartureLocal().isEmpty()){
            hideView(departure);
            hideView(view.findViewById(R.id.departure_text_view));
        }else{
            departure.setText(mPost.getDepartureLocal());
        }

        date.setText(FormatData.formatDateToAmerican(mPost.getRideDate()));

        if(mPost.getDestinationLocal().isEmpty()){
            hideView(destination);
            hideView(view.findViewById(R.id.destination_text_view));
        }else {
            destination.setText(mPost.getDestinationLocal());
        }

        if(mPost.getDescription().isEmpty()){
            hideView(description);
            hideView(view.findViewById(R.id.description_text_view));
        }else{
            description.setText(mPost.getDescription());
        }
//        expiration.setText(mPost.getExpirationDate().toString());

        if(mPost.getKeywords().isEmpty()){
            hideView(keyword);
            hideView(view.findViewById(R.id.keyword_text_view));
        }else{
            keyword.setText(mPost.getKeywords());
        }

        return view;
    }

    private void hideView(View view){
        view.setVisibility(View.GONE);
    }

}
