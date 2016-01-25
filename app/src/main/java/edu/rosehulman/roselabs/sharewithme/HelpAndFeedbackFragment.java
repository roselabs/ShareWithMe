package edu.rosehulman.roselabs.sharewithme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelpAndFeedbackFragment extends Fragment {

    public HelpAndFeedbackFragment() {
        // Required empty constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_feedback, container, false);

        return view;
    }
}
