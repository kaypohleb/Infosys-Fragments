# Get Started

Download source code here: [Link](https://github.com/kaypohleb/Infosys-Fragments)

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

All subclasses of [`Fragment`](https://developer.android.com/reference/android/app/Fragment.html) must include a public no-argument constructor as shown.

The `Fragment` class uses callback methods that are similar to `Activity` callback methods. For example, [`onCreateView()`](https://developer.android.com/reference/android/app/Fragment.html#onCreateView%28android.view.LayoutInflater,%20android.view.ViewGroup,%20android.os.Bundle%29) provides a [`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html) to inflate the `Fragment` UI from the layout resource `fragment_simple`.

1. Open `fragment_simple.xml`. In the layout editor pane, click **Text** to view the XML.
2. Change the attributes for the "Hello blank fragment" `TextView`:

| **`TextView` attribute** | **Value** |
| :--- | :--- |
| `android:id` | `"@+id/fragment_header"` |
| `android:layout_width` | `"wrap_content"` |
| `android:layout_height` | `"wrap_content"` |
| `android:textAppearance` | `"@style/Base.TextAppearance.AppCompat.Medium"` |
| `android:padding` | `"4dp"` |
| `android:text` | `"@string/question_article"` |

The `@string/question_car` resource is defined in the `strings.xml` file in the starter app as `"LIKE THE CAR?"`.

