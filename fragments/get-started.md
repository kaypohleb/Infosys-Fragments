# Creating a Fragment Statically

Download source code here: [Link](https://github.com/kaypohleb/Infosys-Fragments)

```markup
<resources>
    <string name="app_name">FragmentDemo1</string>

    <!-- Placeholder text for this example. -->
    <string name="title1">Hyundai 6000</string>
    <string name="article1">The Hyundai Santa Fe is a sport utility vehicle (SUV) produced by the
        South Korean manufacturer Hyundai since 2000.The Santa Fe was a milestone in the companys
        restructuring program of the late 1990s because the SUV was a hit with American buyers.
        The SUV was so popular that at times, Hyundai had trouble supplying the demand.</string>
    <string name="hello_blank_fragment">Hello blank fragment</string>
    <string name="imageDes">carView</string>
    <!-- Text for the fragment. -->
    <string name="yes">Yes</string>
    <string name="no">No</string>
    <string name="yes_message">ARTICLE: Like</string>
    <string name="no_message">ARTICLE: Thanks</string>
    <string name="question_car">LIKE THE CAR?</string>
    <string name="rating_message">RATE IT</string>

    <!-- Text for the button in FragmentExample2. -->
    <string name="open">Open</string>
    <string name="close">Close</string>

    <!-- Text for the exception message in FragmentCommunicate. -->
    <string name="exception_message">must implement OnFragmentInteractionListener</string>
</resources>
```

### Creating a blank fragment

 1. Choose **File &gt; New &gt; Fragment &gt; Fragment \(Blank\)**.

2. In the **Configure Component** dialog, name the `Fragment` **SimpleFragment**. The **Create layout XML** option should already be selected, and the `Fragment` layout will be filled in as `fragment_simple`.

3. Uncheck the **Include fragment factory methods** and **Include interface callbacks** options. Click **Finish** to create the `Fragment`.

![Dialog box for creating blank fragments](../.gitbook/assets/image%20%283%29.png)

 4. Open `SimpleFragment`, and inspect the code:

```java
public class SimpleFragment extends Fragment {

    public SimpleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, 
                 ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple, 
                 container, false);
```

All sub classes of [`Fragment`](https://developer.android.com/reference/android/app/Fragment.html) must include a public no-argument constructor as shown.

The `Fragment` class uses callback methods that are similar to `Activity` callback methods. For example, [`onCreateView()`](https://developer.android.com/reference/android/app/Fragment.html#onCreateView%28android.view.LayoutInflater,%20android.view.ViewGroup,%20android.os.Bundle%29) provides a [`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html) to inflate the `Fragment` UI from the layout resource `fragment_simple`.

{% hint style="info" %}
All `@string/` resources is defined in the `strings.xml` file in the source code
{% endhint %}

### First, we are going to set our fragment layouts

Open `fragment_simple.xml`. In the layout editor pane, click **Text** to view the XML.

```markup
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:background="@color/fragment_color"
    tools:context=".SimpleFragment">

    <TextView
        android:id="@+id/fragment_header"
        android:layout_marginLeft="@dimen/padding_standard"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="@style/TextAppearance.AppCompat.Medium"
        android:padding="4dp"
        android:text="@string/question_car" />
    <RadioGroup
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:id="@+id/radio_group">
        <RadioButton
            android:id="@+id/radio_button_yes"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginEnd="6dp"
            android:text="@string/yes"/>
        <RadioButton
            android:id="@+id/radio_button_no"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginEnd="6dp"
            android:text="@string/no"/>
    </RadioGroup>


</LinearLayout>
```

Here, we are adding multiple `RadioButton` as a `RadioGroup` to our application so we can switch between states later in our main activity listeners

Now that we have our `fragment`, let's add it in `activity_main.xml` to make it be seen.

```markup
<fragment
    android:id="@+id/fragment"
    android:name="com.example.fragmentdemo1.SimpleFragment"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    tools:layout="@layout/fragment_simple" />
```

Great! Now we can move on to interactions for the fragment.

### Adding Interactions for Fragment

In our `SimpleFragment` class, we need to add actions for our `RadioButtons` that we added in the `xml` file earlier.

```java
 private static final int YES = 0;
    private static final int NO = 1;

    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        final View rootView =
                inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                TextView textView =
                        rootView.findViewById(R.id.fragment_header);
                switch (index) {
                    case YES: // User chose "Yes."
                        textView.setText(R.string.yes_message);
                        break;
                    case NO: // User chose "No."
                        textView.setText(R.string.no_message);
                        break;
                    default: // No choice made.
                        // Do nothing.
                        break;
                }
            }
        });
        // Return the View for the fragment's UI.
        return rootView;
```

