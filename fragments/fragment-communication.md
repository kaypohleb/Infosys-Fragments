# Fragment Communication

Add a third state for the `RadioButton` if neither is selected

```text
private static final int NONE = 2;
public int mRadioButtonChoice = NONE;
```

 Define a listener interface called `OnFragmentInteractionListener`. In it, specify a callback method that you will create, called `onRadioButtonChoice()`:

```text
interface OnFragmentInteractionListener {
    void onRadioButtonChoice(int choice);
}
```

Add a variable for the listener later

 Override the [`onAttach()`](https://developer.android.com/reference/android/app/Fragment.html#onAttach%28android.app.Activity%29) lifecycle method in `SimpleFragment` to capture the host `Activity` interface implementation:

```text
OnFragmentInteractionListener mListener;
```

