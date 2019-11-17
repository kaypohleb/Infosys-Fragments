# Get Started

1. Create a new Android project and use the Empty activity template.

The first thing we will be doing is creating `ScrollView` to accommodate reading if they are too many in built sensors

```markup
<ScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent">
    <TextView
        android:id="@+id/sensorlist"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="placeholder"
        android:textSize="20sp"/>
</ScrollView>
```

2. Let's find all the sensor in our phones that we can use.

```java
mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
```

```java
StringBuilder sensorText = new StringBuilder();
for (Sensor currentSensor: sensorList){
    sensorText.append(currentSensor.getName()).append("\n"));
}
for (Sensor currentSensor : sensorList ) {

}
TextView sensorTextView = (TextView) findViewById(R.id.sensorlist);
sensorTextView.setText(sensorText);
```

Run the app!

You should now be able to see every sensor available for you to use in your phone.

