package edu.rosehulman.roselabs.sharewithme.Drafts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import edu.rosehulman.roselabs.sharewithme.Interfaces.OnListFragmentInteractionListener;
import edu.rosehulman.roselabs.sharewithme.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DraftsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private DraftsBuySellAdapter mDraftsBuySellAdapter;
    private DraftsRidesAdapter mDraftsRidesAdapter;
    private DraftsLostAndFoundAdapter mDraftsLostAndFoundAdapter;

    public DraftsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_drafts, container, false);

        mDraftsBuySellAdapter = new DraftsBuySellAdapter(mListener);
        //TODO FIX VIEWS
        final LinearLayoutManager buySellLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView buyRecyclerView = (RecyclerView) view.findViewById(R.id.buy_sell_recycler_view);
        buyRecyclerView.setLayoutManager(buySellLayoutManager);
        buyRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), null));
        buyRecyclerView.setNestedScrollingEnabled(false);
        buyRecyclerView.setAdapter(mDraftsBuySellAdapter);

        mDraftsRidesAdapter = new DraftsRidesAdapter(mListener);
        final LinearLayoutManager ridesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView ridesRecyclerView = (RecyclerView) view.findViewById(R.id.rides_recycler_view);
        ridesRecyclerView.setLayoutManager(ridesLayoutManager);
        ridesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), null));
        ridesRecyclerView.setNestedScrollingEnabled(false);
        ridesRecyclerView.setAdapter(mDraftsRidesAdapter);

        mDraftsLostAndFoundAdapter = new DraftsLostAndFoundAdapter(mListener);
        final LinearLayoutManager lostAndFoundLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView lostAndFoundRecyclerView = (RecyclerView) view.findViewById(R.id.lost_and_found_recycler_view);
        lostAndFoundRecyclerView.setLayoutManager(lostAndFoundLayoutManager);
        lostAndFoundRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), null));
        lostAndFoundRecyclerView.setNestedScrollingEnabled(false);
        lostAndFoundRecyclerView.setAdapter(mDraftsLostAndFoundAdapter);

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
