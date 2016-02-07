package edu.rosehulman.roselabs.sharewithme.Interfaces;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Created by Thais Faria on 1/29/2016.
 */
public interface OnListFragmentInteractionListener {

    void sendFragmentToInflate(Fragment fragment);

    void sendDialogFragmentToInflate(DialogFragment dialogFragment, String title);

}
