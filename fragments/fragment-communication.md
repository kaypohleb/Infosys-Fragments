# Fragment Communication



**Activity can send data to Fragment and receive data from Fragment**

All Fragment-to-Fragment communication is done through host Activity

![](https://lh4.googleusercontent.com/Bl0CzdHnkEEGiNjopgyifIWnkVjIQ69nv-6Mq1h56nfg01UEQoEMGzcG_3S-s7uhFUlWlDwQL-FD0dTeZJeqwqeQaOPnEDNvJWD_-BMGP6-uEYGQkYmvZMTb7PKif8VPeWTe8sJz1xk)

Add a third state for the `RadioButton` if neither is selected

```java
private static final int NONE = 2;
public int mRadioButtonChoice = NONE;
```

 Define a listener interface called `OnFragmentInteractionListener`. In it, specify a callback method that you will create, called `onRadioButtonChoice()`:

```java
interface OnFragmentInteractionListener {
    void onRadioButtonChoice(int choice);
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

 In `onCreateView()`, get the `mRadioButtonChoice` by adding code to each `case` of the `switch case` block for the radio buttons.

```java
case YES: // User chose "Yes."
    textView.setText(R.string.yes_message);
    mRadioButtonChoice = YES;
    mListener.onRadioButtonChoice(YES);
    break;
case NO: // User chose "No."
    textView.setText(R.string.no_message);
    mRadioButtonChoice = NO;
    mListener.onRadioButtonChoice(NO);
    break;
default: // No choice made.
    mRadioButtonChoice = NONE;
    mListener.onRadioButtonChoice(NONE);
    break;
```

### Implementing `OnFragmentInteractionListener`

Now that we have set up our `SimpleListener` for communication. We need to implement the `OnFragmentInteractionListener` in the activity in order to receive data from fragment

```java
public class MainActivity extends AppCompatActivity
        implements SimpleFragment.OnFragmentInteractionListener {
```

Add  a member variable in `MainActivity` for the choice the user makes with the radio buttons, and set it to the default value

```java
private int mRadioButtonChoice = 2; // The default (no choice).
```

```java
@Override
public void onRadioButtonChoice(int choice) {
    // Keep the radio button choice to pass it back to the fragment.
    mRadioButtonChoice = choice;
    Toast.makeText(this, "Choice is " + Integer.toString(choice),
            Toast.LENGTH_SHORT).show();
}
```

The app should now show a toast when you click it.

### Providing user's choice to fragment

 We have to now set a [`Bundle`](https://developer.android.com/reference/android/os/Bundle.html) and use the `Fragment.`[`setArguments(Bundle)`](https://developer.android.com/reference/android/app/Fragment.html#setArguments%28android.os.Bundle%29) method to supply the construction arguments for the `Fragment`.

A Bundle is like a key-value dictionary which allows to transfer information to other activities, fragments.

First, let's setup a `newInstance()` method to use a `Bundle` and `setArguments`

```java
public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
}
```

Add following code to `onCreateView()` method to retrieve the previously set value of choice

```java
if (getArguments().containsKey(CHOICE)) {
    // A choice was made, so get the choice.
    mRadioButtonChoice = getArguments().getInt(CHOICE);
    // Check the radio button choice.
    if (mRadioButtonChoice != NONE) {
        radioGroup.check
                 (radioGroup.getChildAt(mRadioButtonChoice).getId());
    }
}
```

Lastly, we need to change our functions to 

```text
public void displayFragment(){
    SimpleFragment simpleFragment = SimpleFragment.newInstance();
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.fragment_container,simpleFragment).addToBackStack(null).commit();
    sButtonl.setText(R.string.button_carClose);
    isFragmentDisplayed = true;
}
public void closeFragment(){
    FragmentManager fragmentManager = getSupportFragmentManager();
    RatingFragment simpleFragment = (SimpleFragment)fragmentManager.findFragmentById(R.id.fragment_container);
    if(ratingFragment!=null){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(simpleFragment).addToBackStack(null).commit();
    }
    sButtonl.setText(R.string.button_car);
    isFragmentDisplayed = false;

}
```

