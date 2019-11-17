# Orientation

Let's set up the layout for our app! `activity_main.xml`

```markup
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.tiltorientation.MainActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/label_group"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
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

        <TextView
            android:id="@+id/value_roll"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/value_format"
            android:textAppearance="@style/Base.TextAppearance.AppCompat.Medium"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@id/value_pitch"/>
    </androidx.constraintlayout.widget.ConstraintLayout>

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

We will also add float arrays so we can have copies of our sensor data

```java
private float[] mAccelerometerData = new float[3];
private float[] mMagnetometerData = new float[3];
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

Registering Listeners on our device if we can find them

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

Unregistering Listeners on our device when we close the app \(for Battery Life!\)

```java
@Override
    protected void onStop() {
        super.onStop();

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is stopped.
        mSensorManager.unregisterListener(this);
    }
```

finally implement the `SensorEventListener` so we can register changes

```java
public class MainActivity extends AppCompatActivity implements SensorEventListener {
```

We want to generate a rotation matrix from the `raw accelerometer` and `magnetometer data`. which helps to use in the next step to get the device orientation.

#### Rotation Matrix

A [rotation matrix](https://en.wikipedia.org/wiki/Rotation_matrix) is a linear algebra term that translates the sensor data from one coordinate system to another—in this case, from the device's coordinate system to the Earth's coordinate system. That matrix is an array of nine `float` values, because each point \(on all three axes\) is expressed as a 3D vector.

The device-coordinate system is a standard 3-axis \(_x_, _y_, _z_\) coordinate system relative to the device's screen when it is held in the default or natural orientation. Most sensors use this coordinate system. In this orientation:

* The _x_-axis is horizontal and points to the right edge of the device.
* The _y_-axis is vertical and points to the top edge of the device.
* The _z_-axis extends up from the surface of the screen. Negative _z_ values are behind the screen.

![](https://codelabs.developers.google.com/codelabs/advanced-android-training-sensor-orientation/img/1e6e9d6515a7d3f3.png)

The Earth's coordinate system is also a 3-axis system, but relative to the surface of the Earth itself. In the Earth's coordinate system:

* The _y_-axis points to magnetic north along the surface of the Earth.
* The _x_-axis is 90 degrees from _y_, pointing approximately east.
* The _z_-axis extends up into space. Negative _z_ extends down into the ground.

![](https://codelabs.developers.google.com/codelabs/advanced-android-training-sensor-orientation/img/602019bcfa292466.png)

A reference to the array for the rotation matrix is passed into the `getRotationMatrix()` method and modified in place. The second argument to `getRotationMatrix()` is an inclination matrix, which you don't need for this app. You can use null for this argument.

The `getRotationMatrix()` method returns a boolean \(the `rotationOK` variable\), which is `true` if the rotation was successful. The boolean might be `false` if the device is free-falling \(meaning that the force of gravity is close to 0\), or if the device is pointed very close to magnetic north. The incoming sensor data is unreliable in these cases, and the matrix can't be calculated. Although the boolean value is almost always `true`, it's good practice to check that value anyhow.

* _Azimuth_: The direction \(north/south/east/west\) the device is pointing. 0 is magnetic north.
* _Pitch_: The top-to-bottom tilt of the device. 0 is flat.
* _Roll_: The left-to-right tilt of the device. 0 is flat.

{% hint style="info" %}
All three angles are measured in `radians,` and range from -π \(-3.141\) to π.
{% endhint %}

```java
@Override
public void onSensorChanged(SensorEvent event) {
    int sensorType = event.sensor.getType();
    switch (sensorType) {
        case Sensor.TYPE_ACCELEROMETER:
            mAccelerometerData = event.values.clone();
            break;
        case Sensor.TYPE_MAGNETIC_FIELD:
            mMagnetometerData = event.values.clone();
            break;
        default:
            return;
    }
    float[] rotationMatrix = new float[9];
    boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix, null, mAccelerometerData, mMagnetometerData)
    float orientationValues[] = new float[3];
    if (rotationOK) {
        SensorManager.getOrientation(rotationMatrix, orientationValues);
    };
    float azimuth = orientationValues[0];
    float pitch = orientationValues[1];
    float roll = orientationValues[2];
    mTextSensorAzimuth.setText(getResources().getString(R.string.value_format, azimuth));
    mTextSensorPitch.setText(getResources().getString(R.string.value_format, pitch));
    mTextSensorRoll.setText(getResources().getString(R.string.value_format, roll));
}
```

Run the app! The values should now update

1. Add a new file called `spot.xml` to the project, in the `res/drawable` directory. \(Create the directory if needed.\)
2. Replace the selector tag in `spot.xml` with an oval shape drawable whose color is solid black \(`"@android:color/black"`\)

```markup
<shape
   xmlns:android="http://schemas.android.com/apk/res/android"
   android:shape="oval">
   <solid android:color="@android:color/black"/>
</shape>
```

Let's add four circles with 50% Opacity to show 

```markup
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.tiltorientation.MainActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/label_group"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
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

        <!-- Values -->
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

        <TextView
            android:id="@+id/value_roll"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/value_format"
            android:textAppearance="@style/Base.TextAppearance.AppCompat.Medium"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@id/value_pitch"/>
    </androidx.constraintlayout.widget.ConstraintLayout>

    <ImageView
        android:id="@+id/spot_top"
        android:layout_width="84dp"
        android:layout_height="84dp"
        android:layout_margin="8dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:srcCompat="@drawable/spot"
        android:alpha="0.05"/>

    <ImageView
        android:id="@+id/spot_bottom"
        android:layout_width="84dp"
        android:layout_height="84dp"
        android:layout_marginBottom="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:srcCompat="@drawable/spot"
        android:alpha="0.05" />

    <ImageView
        android:id="@+id/spot_right"
        android:layout_width="84dp"
        android:layout_height="84dp"
        android:layout_marginEnd="8dp"
        android:layout_marginRight="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/spot"
        android:alpha="0.05"/>

    <ImageView
        android:id="@+id/spot_left"
        android:layout_width="84dp"
        android:layout_height="84dp"
        android:layout_marginLeft="8dp"
        android:layout_marginStart="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/spot"
        android:alpha="0.05" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

  In `MainActivity`

```java
private ImageView mSpotTop;
private ImageView mSpotBottom;
private ImageView mSpotLeft;
private ImageView mSpotRight;
```

```java
mSpotTop = (ImageView) findViewById(R.id.spot_top);
mSpotBottom = (ImageView) findViewById(R.id.spot_bottom);
mSpotLeft = (ImageView) findViewById(R.id.spot_left);
mSpotRight = (ImageView) findViewById(R.id.spot_right);
```

`onSensorChange()` : if the change is not enough, set it to 0



```text
if (Math.abs(pitch) < 0.5f) {
    pitch = 0;
}
if (Math.abs(roll) < 0.5f) {
    roll = 0;
}
```

