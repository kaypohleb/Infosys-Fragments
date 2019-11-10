package com.example.fragmentdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RatingFragment.OnFragmentInteractionListener{
    Button sButtonl;
    private Boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";
    private float mRatingChoice = 0;
    static final String STATE_CHOICE = "user_choice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sButtonl = findViewById(R.id.button);
        sButtonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFragmentDisplayed){
                    displayFragment();
                }else{
                    closeFragment();
                }
            }
        });
        if(savedInstanceState!=null){
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            mRatingChoice = savedInstanceState.getInt(STATE_CHOICE);
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean(STATE_FRAGMENT,isFragmentDisplayed);
        savedInstanceState.putFloat(STATE_CHOICE, mRatingChoice);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void displayFragment(){
        RatingFragment ratingFragment = RatingFragment.newInstance(mRatingChoice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,ratingFragment).addToBackStack(null).commit();
        sButtonl.setText(R.string.button_carClose);
        isFragmentDisplayed = true;
    }
    public void closeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        RatingFragment ratingFragment = (RatingFragment)fragmentManager.findFragmentById(R.id.fragment_container);
        if(ratingFragment!=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(ratingFragment).addToBackStack(null).commit();
        }
        sButtonl.setText(R.string.button_car);
        isFragmentDisplayed = false;

    }


    @Override
    public void onRatingChoice(float choice) {
        mRatingChoice = choice;
        Toast.makeText(this,"Choice is "+ Float.toString(choice),Toast.LENGTH_SHORT).show();
    }
}
