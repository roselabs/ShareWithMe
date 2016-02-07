package edu.rosehulman.roselabs.sharewithme.Interfaces;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellAdapter;
import edu.rosehulman.roselabs.sharewithme.Drafts.DraftsBuySellAdapter;
import edu.rosehulman.roselabs.sharewithme.Drafts.DraftsRidesAdapter;
import edu.rosehulman.roselabs.sharewithme.LostAndFound.LostAndFoundAdapter;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesAdapter;

/**
 * Created by Thais Faria on 1/29/2016.
 */
public interface OnListFragmentInteractionListener {

    void sendAdapterToMain(BuySellAdapter adapter);

    void sendAdapterToMain(RidesAdapter adapter);

    void sendAdapterToMain(LostAndFoundAdapter adapter);

    void sendAdapterToMain(DraftsBuySellAdapter adapter);

    void sendAdapterToMain(DraftsRidesAdapter adapter);

    void sendFragmentToInflate(Fragment fragment);

    void sendDialogFragmentToInflate(DialogFragment dialogFragment, String title);

}
