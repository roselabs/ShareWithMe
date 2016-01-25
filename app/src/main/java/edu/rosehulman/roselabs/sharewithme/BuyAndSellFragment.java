package edu.rosehulman.roselabs.sharewithme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyAndSellFragment extends Fragment {

    public BuyAndSellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_and_sell, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.buy_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBuySellPostDialog dialog = new CreateBuySellPostDialog();
                dialog.show(getFragmentManager(), "Create new post on Buy/Sell");
            }
        });

        return view;
    }

}
