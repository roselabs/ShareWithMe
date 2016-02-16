package edu.rosehulman.roselabs.sharewithme.LostAndFound;

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

public class LostAndFoundFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private LostAndFoundAdapter mAdapter;

    public LostAndFoundFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lost_and_found, container, false);
        mAdapter = new LostAndFoundAdapter(mListener);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.buy_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateLostAndFoundPostDialog dialog = new CreateLostAndFoundPostDialog();
                dialog.show(getFragmentManager(), "Create new post on Lost and Found");
            }
        });

        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroupFilter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.lost_radio_button_filter){
                    mAdapter.setFilterPost(0);
                }else if (checkedId == R.id.found_radio_button_filter){
                    mAdapter.setFilterPost(1);
                }else {
                    mAdapter.setFilterPost(2);
                }
            }
        });

        return rootView;
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
