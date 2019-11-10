# Fragment Communication



**Activity can send data to Fragment and receive data from Fragment**

All Fragment-to-Fragment communication is done through host Activity

![](https://lh4.googleusercontent.com/Bl0CzdHnkEEGiNjopgyifIWnkVjIQ69nv-6Mq1h56nfg01UEQoEMGzcG_3S-s7uhFUlWlDwQL-FD0dTeZJeqwqeQaOPnEDNvJWD_-BMGP6-uEYGQkYmvZMTb7PKif8VPeWTe8sJz1xk)

### `RatingFragment`Setup

Add a  `String`variable and `float` value, we will be using this to place inside the `Bundle`

```java
private static final String CHOICE="choice";
public float choice;
```

 Define a listener interface called `OnFragmentInteractionListener`. In it, specify a callback method that you will create, called `onRadioButtonChoice()`:

```java
interface OnFragmentInteractionListener {
    void onRatingChoice(int choice);
}
```

Add a variable for the listener later

```java
OnFragmentInteractionListener mListener;
```

 Override the [`onAttach()`](https://developer.android.com/reference/android/app/Fragment.html#onAttach%28android.app.Activity%29) lifecycle method in `SimpleFragment` to capture the host `Activity` interface implementation:

```java
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
```

The `onAttach()` method is called as soon as the `Fragment` is associated with the `Activity`. The code makes sure that the host `Activity` has implemented the callback interface. If not, it throws an exception.

{% hint style="info" %}
The string resource `exception_message` is included in the **source code** for the text `"must implement OnFragmentInteractionListener"`
{% endhint %}



Overall, your class should end up looking like this.

```java
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

```

### Implementing `OnFragmentInteractionListener`

Now that we have set up our `RatingListener` for communication. We need to implement the `OnFragmentInteractionListener` in the activity in order to receive data from fragment

```java
public class MainActivity extends AppCompatActivity
        implements RatingFragment.OnFragmentInteractionListener {
```

Add  a member variable in `MainActivity` for the choice the user makes with the `RatingBar`, and set it to the default value

```java
private float mRatingChoice = 0; // The default (no choice).
static final String STATE_CHOICE = "user_choice";
```

```java
@Override
    public void onRatingChoice(float choice) {
        mRatingChoice = choice;
        Toast.makeText(this,"Choice is "+ Float.toString(choice),Toast.LENGTH_SHORT).show();
}
```

Set up the `onSaveInstanceState(Bundle)` to save our float variable and states for the `Activity`

```java
@Override
public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean(STATE_FRAGMENT,isFragmentDisplayed);
        savedInstanceState.putFloat(STATE_CHOICE, mRatingChoice);
        super.onSaveInstanceState(savedInstanceState);
    }
```

The app should now show a toast when you click it.

### Providing user's choice to fragment \(Main

 We have to now set a [`Bundle`](https://developer.android.com/reference/android/os/Bundle.html) and use the `Fragment.`[`setArguments(Bundle)`](https://developer.android.com/reference/android/app/Fragment.html#setArguments%28android.os.Bundle%29) method to supply the construction arguments for the `Fragment`.

A Bundle is like a key-value dictionary which allows to transfer information to other activities, fragments.

First, let's setup a `newInstance()` method to use a `Bundle` and `setArguments`

```java
 public static RatingFragment newInstance(float choice) {
        RatingFragment fragment = new RatingFragment();
        Bundle arguments = new Bundle();
        arguments.putFloat(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }
```

Add following code to `onCreateView()` method to retrieve the previously set value of choice

```java
if (getArguments().containsKey(CHOICE)) {
            // A choice was made, so get the choice.
            choice = getArguments().getFloat(CHOICE);
            // Check the radio button choice.
            if (choice!=0) {
                rB.setRating(choice);
            }
        }
```

Lastly, we need to change our functions in `MainActivity` to `RatingFragment.newInstance(mRatingChoice);`

```java
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
```



Your app should be communicating the choice from the `Fragment` to the `Activity`, and then back to the `Fragment`which should display the choice you made previously always

#### End Result:

```java
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
```

From this point, there are more complicated paths and communications you can perform, but these steps lay a foundation in using them.

