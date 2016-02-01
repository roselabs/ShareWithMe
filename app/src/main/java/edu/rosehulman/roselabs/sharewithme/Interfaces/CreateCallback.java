package edu.rosehulman.roselabs.sharewithme.Interfaces;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellPost;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesPost;

/**
 * Created by Thais Faria on 1/29/2016.
 */
public interface CreateCallback {

    void onCreatePostFinished(BuySellPost post);

    void onCreatePostFinished(RidesPost post);

}