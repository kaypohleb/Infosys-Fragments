# Environmental Sensor

* The light sensor measures ambient light in [`lux`](https://www.google.com/url?q=https://en.wikipedia.org/wiki/Lux&sa=D&ust=1503084804609000&usg=AFQjCNFUzNgexsW9khE5Cp84b8YZ56viVQ), a standard unit of illumination. The light sensor typically is used to automatically adjust screen brightness.
* The proximity sensor measures when the device is close to another object. The proximity sensor is often used to turn off touch events on a phone's screen when you answer a phone call, so that touching your phone to your face does not accidentally launch apps or otherwise interfere with the device's operation.

Firstly, let's use replace the entire `ScrollView` component. with the block of code below in `activity_main.xml`

```markup
<TextView
        android:id="@+id/label_light"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Light Sensor: %1$.2f"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>
    <TextView
        android:id="@+id/label_proximity"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Proximity Sensor: %1$.2f"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="@id/label_light"/>
```

but what if our phones don't have this sensor?!?!?!

```markup
<string name="error_no_sensor">No sensor</string>
```

We'll need to declare the `Sensors` themselves

```text
private Sensor mSensorProximity;
private Sensor mSensorLight;
```

Let's also get the view of our `TextView`s

```text
private TextView mTextSensorLight;
private TextView mTextSensorProximity;
```

Let's enable the `Sensors` and `TextViews` by assigning them in `onCreate()` in `MainActivity.class`

```java
mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
mTextSensorLight = findViewById(R.id.label_light);
mTextSensorProximity = findViewById(R.id.label_proximity);
String sensor_error = getResources().getString(R.string.error_no_sensor);
```

Here we are testing for the existences of the sensors

```java
if (mSensorLight == null) {
   mTextSensorLight.setText(sensor_error);
}
if (mSensorProximity == null) {
   mTextSensorProximity.setText(sensor_error);
}
```

`SensorEventListener` interface allows us to listen for events that the Android sensor framework generates. 

* [`onSensorChanged()`](https://developer.android.com/reference/android/hardware/SensorEventListener.html#onSensorChanged%28android.hardware.SensorEvent%29): Called when new sensor data is available. You will use this callback most often to handle new sensor data in your app.
* [`onAccuracyChanged()`](https://developer.android.com/reference/android/hardware/SensorEventListener.html#onAccuracyChanged%28android.hardware.Sensor,%20int%29): Called if the sensor's accuracy changes, so your app can react to that change. Most sensors, including the light and proximity sensors, do not report accuracy changes. In this app, you leave `onAccuracyChanged()` empty.

```java
public class MainActivity extends AppCompatActivity implements SensorEventListener
```

```java
@Override
protected void onStart() {
   super.onStart();

   if (mSensorProximity != null) {
      mSensorManager.registerListener(this, mSensorProximity,
         SensorManager.SENSOR_DELAY_NORMAL);
   }
   if (mSensorLight != null) {
      mSensorManager.registerListener(this, mSensorLight,
         SensorManager.SENSOR_DELAY_NORMAL);
   }
}
```

{% hint style="info" %}
 Note that we are overriding the `onStart()` activity [lifecycle](../fragments/communication.md) method to register your sensor listeners. 

This allows us to ensure sensors to continue running even if the app is in multiwindow mode which uses `onResume()` and `onPause()` Listening to incoming sensor data uses device power and consumes battery life. 

Don't register your listeners in `onCreate()`, as that would cause the sensors to be on and sending data \(using device power\) even when your app was not in the foreground. Use the `onStart()` and `onStop()` methods to register and unregister your sensor listeners.
{% endhint %}

Each sensor that your app uses needs its own listener, and you should make sure that those sensors exist before you register a listener for them. Use the [`registerListener()`](https://developer.android.com/reference/android/hardware/SensorManager.html#registerListener%28android.hardware.SensorEventListener,%20android.hardware.Sensor,%20int%29) method from the `SensorManager` to register a listener. This method takes three arguments:

* An app or activity [`Context`](https://developer.android.com/reference/android/content/Context.html). You can use the current activity \(`this`\) as the context.
* The `Sensor` object to listen to.
* A delay constant from the `SensorManager` class. The delay constant indicates how quickly new data is reported from the sensor. Sensors can report a lot of data very quickly, but more reported data means that the device consumes more power. Make sure that your listener is registered with the minimum amount of new data it needs. In this example you use the slowest value \([`SensorManager.SENSOR_DELAY_NORMAL`](https://developer.android.com/reference/android/hardware/SensorManager.html#SENSOR_DELAY_NORMAL)\). For more data-intensive apps such as games, you may need a faster rate such as [`SENSOR_DELAY_GAME`](https://developer.android.com/reference/android/hardware/SensorManager.html#SENSOR_DELAY_GAME) or [`SENSOR_DELAY_FASTEST`](https://developer.android.com/reference/android/hardware/SensorManager.html#SENSOR_DELAY_FASTEST).

Implement the `onStop()` [lifecycle ](../fragments/communication.md)method to unregister your sensor listeners when the app pauses: which prevents the device from using power when the app is not visible.

```java
@Override
protected void onStop() {
   super.onStop();
   mSensorManager.unregisterListener(this);
}
```

`onSensorChange():` `SensorEvent` object includes important properties of the event, such as which sensor is reporting new data, and the new data values. Use the `sensor` property of the `SensorEvent` to get a `Sensor` object, and then use `getType()`

We use `values[0]` because the light sensor only has one perimeter values to return unlike an accelerator which has 3 values for its axis, where we will use  `values[0]`, `values[1]`, and `values[2]` instead

```java
int sensorType = event.sensor.getType();
float currentValue = event.values[0];
switch (sensorType) {
    // Event came from the light sensor.
    case Sensor.TYPE_LIGHT:
        mTextSensorLight.setText(getResources().getString(R.string.label_light, currentValue));
        break;
    case Sensor.TYPE_PROXIMITY
        mTextSensorProximity.setText(getResources().getString(R.string.label_proximity, currentValue));
        break;
    default:
        // do nothing
}
```

 When you call `getString()` to get the string from the resources, you include values to substitute into the string where the placeholder codes are. The part of the string that is not made up of placeholders \(`"Light Sensor: "`\) is passed through to the new string.

