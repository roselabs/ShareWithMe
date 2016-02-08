package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

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

import edu.rosehulman.roselabs.sharewithme.Interfaces.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.R;

public class BuyAndSellFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private BuySellAdapter mAdapter;

    public BuyAndSellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_and_sell, container, false);
        mAdapter = new BuySellAdapter(mListener);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.buy_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBuySellPostDialog dialog = new CreateBuySellPostDialog();
                dialog.show(getFragmentManager(), "Create new post on Buy and Sell");
            }
        });

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupFilter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.buy_radio_button_filter){
                    mAdapter.setFilter(0);
                }else if(checkedId == R.id.sell_radio_button_filter){
                    mAdapter.setFilter(1);
                }else{
                    mAdapter.setFilter(2);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
