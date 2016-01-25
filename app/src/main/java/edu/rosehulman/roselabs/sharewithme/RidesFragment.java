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
public class RidesFragment extends Fragment {

    public RidesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //substitute by a rides fragment view
        View view = inflater.inflate(R.layout.fragment_rides, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.buy_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRidesPostDialog dialog = new CreateRidesPostDialog();
                dialog.show(getFragmentManager(), "Create new post on Rides");
            }
        });

        return view;
    }

}
