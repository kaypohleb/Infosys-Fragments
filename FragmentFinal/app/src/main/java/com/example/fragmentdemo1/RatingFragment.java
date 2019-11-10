package com.example.fragmentdemo1;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment {
    private static final String CHOICE="choice";
    public float choice;
    OnFragmentInteractionListener mListener;

    interface OnFragmentInteractionListener {
        void onRatingChoice(float choice);
    }

    public RatingFragment() {
        // Required empty public constructor

    }

    public static RatingFragment newInstance(float choice) {
        RatingFragment fragment = new RatingFragment();
        Bundle arguments = new Bundle();
        arguments.putFloat(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_rating, container, false);
        final RatingBar rB = rootView.findViewById(R.id.ratingBar);
        if (getArguments().containsKey(CHOICE)) {
            // A choice was made, so get the choice.
            choice = getArguments().getFloat(CHOICE);
            // Check the radio button choice.
            if (choice!=0) {
                rB.setRating(choice);
            }
        }

        rB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String s = getString(R.string.rating_message);
                choice = v;
                mListener.onRatingChoice(v);
                //Toast.makeText(getActivity().getApplicationContext(),s+ " "+ v,Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }

}
