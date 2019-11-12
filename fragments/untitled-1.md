# Creating a Fragment Dynamically

Let's continue from our current activity!

The main difference in creating a fragment is that you have to manage the `Fragment` using [`FragmentManager`](https://developer.android.com/reference/android/app/FragmentManager.html) and [`FragmentTransaction`](https://developer.android.com/reference/android/app/FragmentTransaction.html) statements that can add, remove, and replace a `Fragment`

### Creating a Fragment Class

Just as you did just now,  create a fragment called `RatingFragment` .

### Set the Layout of the Class

This time, let's use a `RatingBar` instead of a `RadioGroup` with `RadioButtons`

```markup
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:background="@color/fragment_color"
    tools:context=".RatingFragment">

    <TextView
        android:id="@+id/carText"
        android:layout_marginLeft="@dimen/padding_standard"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="@style/TextAppearance.AppCompat.Medium"
        android:padding="4dp"
        android:text="@string/rating_message" />
    <RatingBar
        android:id="@+id/ratingBar"
        style="@android:style/Widget.DeviceDefault.RatingBar.Small"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:numStars="6"
        android:padding="8dp"
        android:rating="0"
        android:stepSize="0.5"
        android:isIndicator="false"
        />

</LinearLayout>
```

Now just as we did before, we can edit and place interactivity in our fragment right away.

In this new fragment, we have a new function `newInstance()`. This allows us to instantiate the `Fragment` in the `MainActivity` later on.

```java
 public static RatingFragment newInstance() {

        RatingFragment fragment = new RatingFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_rating, container, false);
        final RatingBar rB = rootView.findViewById(R.id.ratingBar);
        rB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String s = getString(R.string.rating_message);
                Toast.makeText(getActivity().getApplicationContext(),s+ " "+ v,Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
```

{% hint style="info" %}
Note:[`Toast`](https://developer.android.com/guide/topics/ui/notifiers/toasts) here displays simple messages in a small popup for a selected amount of time.
{% endhint %}

Great! Now all we need is to edit the rest on the `MainActivity`  and `activity_main.xml`

### Creating functions to display/hide Fragments

We are going to use a `<FrameLayout>` as a fragment container instead of a &lt;fragment&gt; in `activity_main.xml`

First, let's specify [`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html) for `Fragment` in layout

```markup
<FrameLayout
    android:id="@+id/fragment_container"
    android:name="com.example.fragmentdemo1.RatingFragment"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toBottomOf="@id/fragment"
    tools:layout="@layout/fragment_rating" />
```

Add the following code within the `MainActivity`class.

```java
    public void displayFragment(){
        RatingFragment ratingFragment = RatingFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,ratingFragment).addToBackStack(null).commit();
        sButtonl.setText(R.string.close);
        isFragmentDisplayed = true;
    }
    public void closeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        RatingFragment ratingFragment = (RatingFragment)fragmentManager.findFragmentById(R.id.fragment_container);
        if(ratingFragment!=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(ratingFragment).addToBackStack(null).commit();
        }
        sButtonl.setText(R.string.open);
        isFragmentDisplayed = false;

    }
```

What the code is doing here is instantiating `RatingFragment`and `FragmentManager` 

Use `getSupportFragmentManager()` for compatibility

Use [`FragmentTransaction`](https://developer.android.com/reference/androidx/fragment/app/FragmentTransaction)  for adding/removing/replacing dynamically.

#### FragmentManager:

`FragmentManager` interacts with Fragment which are within an `Activity`.

| Function | What it does |
| :--- | :--- |
| **`beginTransaction()`** | start fragment transaction and returns `FragmentTransaction` |
| **`findFragmentById(int id)`** | by passing id, it returns fragment instance. |

#### FragmentTransaction

`FragmentTransaction`performs fragment operations. Find some methods.

| Function | What it does |
| :--- | :--- |
| **`add(int containerViewId, Fragment fragment)`** | adds a given fragment to activity state. |
| **`attach(Fragment fragment)`** | The detached fragment can be re-attached by using this method. |
| **`detach(Fragment fragment)`** | Detaches the given fragment from UI. Fragment state is still managed by `FragmentManager`. |
| **`remove(Fragment fragment)`** | Removes the given fragment from UI and container. |
| **`replace(int containerViewId, Fragment fragment)`** | For the given container view id, we can replace existing fragment by new given fragment. |
| **`commit()`** | Transaction is committed. |

Fragment operations are wrapped into a transaction:

* Start transaction with [`beginTransaction()`](https://developer.android.com/reference/android/app/FragmentManager.html#beginTransaction%28%29)\`\`
* Do all Fragment operations \(`add`, `remove`, etc.\)
* `addingtoBackStack(null)` saves transaction to the back stack so the user can reverse the transaction and bring back the previous fragment by pressing the Back button.
* End transaction with [`commit()`](https://developer.android.com/reference/android/app/FragmentTransaction.html#commit%28%29)

Alright, now we just need to add a `Button` and set up its `OnClickListener()` to our functions

First, place the button in the `activity_main.xml` layout

```markup
<Button
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    android:id="@+id/button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/button_car"
    app:layout_constraintTop_toBottomOf="@id/article"
    />
```

Now, find its view in the `MainActivity` and `setOnClickListener()`

```java
Button sButtonl;
    private Boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";

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
        }
        
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean(STATE_FRAGMENT,isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }
    
```

And you should be able to build and run your app!

