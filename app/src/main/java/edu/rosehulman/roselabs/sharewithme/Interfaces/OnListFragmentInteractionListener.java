package edu.rosehulman.roselabs.sharewithme.Interfaces;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellPost;
import edu.rosehulman.roselabs.sharewithme.LostAndFound.LostAndFoundPost;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesDetailFragment;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesPost;

/**
 * Created by Thais Faria on 1/29/2016.
 */
public interface OnListFragmentInteractionListener {

    void sendFragmentToInflate(Fragment fragment);

    void sendProfileFragmentToInflate(String userKey);

    void sendDialogFragmentToInflate(DialogFragment dialogFragment, String title);

    void inactivatePost(BuySellPost mPost);

    void inactivatePost(RidesPost mPost);

    void inactivatePost(LostAndFoundPost mPost);
}
