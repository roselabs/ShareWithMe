package edu.rosehulman.roselabs.sharewithme.Interfaces;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellPost;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesPost;

/**
 * Created by Thais Faria on 1/29/2016.
 */
public interface CreateCallback {

    public void onCreatePostFinished(BuySellPost post);

    public void onCreatePostFinished(RidesPost post);

}
