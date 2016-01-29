package edu.rosehulman.roselabs.sharewithme.Interfaces;

import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuyAndSellFragment;
import edu.rosehulman.roselabs.sharewithme.BuyAndSell.BuySellAdapter;
import edu.rosehulman.roselabs.sharewithme.Rides.RidesAdapter;

/**
 * Created by Thais Faria on 1/29/2016.
 */
public interface OnListFragmentInteractionListener {

    public void sendAdapterToMain(BuySellAdapter adapter);

    public void sendAdapterToMain(RidesAdapter adapter);

}
