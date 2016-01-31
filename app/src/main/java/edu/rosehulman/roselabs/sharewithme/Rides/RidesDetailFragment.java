package edu.rosehulman.roselabs.sharewithme.Rides;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import edu.rosehulman.roselabs.sharewithme.FormatData.FormatData;
import edu.rosehulman.roselabs.sharewithme.Interfaces.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RidesDetailFragment extends Fragment {

    private RidesPost mPost;

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

        String optionValue;
        if(mPost.isOffer())
            optionValue = "Offer";
        else
            optionValue = "Request";

        option.setText(optionValue);
        price.setText("$ " + mPost.getPrice());
        title.setText(mPost.getTitle());
        departure.setText(mPost.getDepartureLocal());
        date.setText(FormatData.formatDateToAmerican(mPost.getRideDate()));
        destination.setText(mPost.getDestinationLocal());
        description.setText(mPost.getDescription());
//        expiration.setText(mPost.getExpirationDate().toString());
        keyword.setText(mPost.getKeywords());

        return view;
    }

}
