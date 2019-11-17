# Orientation



Let's set up the layout for our app! `activity_main.xml`

```markup
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.constraint.ConstraintLayout
        android:id="@+id/label_group"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/base_margin"
        android:orientation="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

    <TextView
        android:id="@+id/label_azimuth"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/label_azimuth_string"
        style="@style/Label"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="@+id/label_group"/>

    <TextView
        android:id="@+id/label_pitch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/label_pitch_string"
        style="@style/Label"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/label_azimuth"/>

    <TextView
        android:id="@+id/label_roll"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/label_roll_string"
        style="@style/Label"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/label_pitch"/>


    <TextView
        android:id="@+id/value_azimuth"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="8dp"
        android:layout_marginStart="8dp"
        android:text="@string/value_format"
        android:textAppearance="@style/Base.TextAppearance.AppCompat.Medium"
        app:layout_constraintLeft_toRightOf="@id/label_azimuth"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="@+id/label_group"/>

    <TextView
        android:id="@+id/value_pitch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/value_format"
        android:textAppearance="@style/Base.TextAppearance.AppCompat.Medium"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/value_azimuth"/>

    </android.support.constraint.ConstraintLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

Add the following code in `styles.xml`

```markup
<style name="Label" parent="Base.TextAppearance.AppCompat.Medium">
    <item name="android:textStyle">bold</item>
</style>
```

This line locks the activity in `portrait mode`, to prevent the app from automatically rotating the activity as you tilt the device.

```java
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
```

Declaring our `Sensors` ,`SensorManagers`and our `TextView`

in top of `MainActivity,class`

```java
private SensorManager mSensorManager;
private Sensor accelerometer;
private Sensor magneticField;
private TextView mTextSensorAzimuth;
private TextView mTextSensorPitch;
private TextView mTextSensorRoll;
```

We will also add a base value for acceptable non-zero drift.

```java
private static final float VALUE_DRIFT = 0.05f;
```

in `onCreate()`

```java
mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
magneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
mTextSensorAzimuth = (TextView) findViewById(R.id.value_azimuth);
mTextSensorPitch = (TextView) findViewById(R.id.value_pitch);
mTextSensorRoll = (TextView) findViewById(R.id.value_roll);
```

```java
@Override
    protected void onStart() {
        super.onStart();

        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetometer != null) {
            mSensorManager.registerListener(this, mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
```

```java
@Override
    protected void onStop() {
        super.onStop();

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is stopped.
        mSensorManager.unregisterListener(this);
    }
```

