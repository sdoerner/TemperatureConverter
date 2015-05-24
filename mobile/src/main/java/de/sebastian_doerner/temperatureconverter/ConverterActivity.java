package de.sebastian_doerner.temperatureconverter;

import static de.sebastian_doerner.temperatureconverter.TemperatureCalculator.getFahrenheitFromCelsius;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

public final class ConverterActivity extends Activity {

    private static final String TAG = ConverterActivity.class.getSimpleName();

    private static final int INITIAL_TEMPERATURE_CELSIUS = 16;
    private static final int MIN_TEMPERATURE_CELSIUS = -273;
    private static final int MAX_TEMPERATURE_CELSIUS = 100;

    private static final double VELOCITY_TO_TEMPERATURE_DELTA_RATIO = 1000.0;
    private static final long MIN_TIME_BETWEEN_SMALL_EVENTS_MS = 200;

    int temperatureCelsius;

    long lastProcessedEvent = 0l;

    private TextView celsiusView;
    private TextView fahrenheitView;
    private VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        celsiusView = (TextView) findViewById(R.id.celsius_view);
        fahrenheitView = (TextView) findViewById(R.id.fahrenheit_view);
        setTemperatureCelsius(INITIAL_TEMPERATURE_CELSIUS);
    }

    private void setTemperatureCelsius(int temperatureCelsius) {
        temperatureCelsius =
                clamp(MIN_TEMPERATURE_CELSIUS, temperatureCelsius, MAX_TEMPERATURE_CELSIUS);
        this.temperatureCelsius = temperatureCelsius;
        String celsiusString = getString(R.string.celsius, temperatureCelsius);
        celsiusView.setText(celsiusString);

        int temperatureFahrenheit = getFahrenheitFromCelsius(temperatureCelsius);
        String fahrenheitString = getString(R.string.fahrenheit, temperatureFahrenheit);
        fahrenheitView.setText(fahrenheitString);
    }

    private int clamp(int minValue, int targetValue, int maxValue) {
        return Math.max(Math.min(targetValue, maxValue), minValue);
    }

    private void handleMoveEventWithVelocity(float velocity) {
        Log.i(TAG, "velocity: " + velocity);
        double magnitude = Math.abs(velocity / VELOCITY_TO_TEMPERATURE_DELTA_RATIO);
        int magnitudeInt = (int) Math.ceil(magnitude);
        int celsiusTargetDelta = magnitudeInt * (velocity > 0 ? -1 : 1);

        // For small magnitudes only process events ever so often to make it easier to make small
        // adjustments.
        long timeStamp = System.currentTimeMillis();
        if (magnitudeInt > 0 &&
                timeStamp - lastProcessedEvent > MIN_TIME_BETWEEN_SMALL_EVENTS_MS / magnitudeInt) {
            lastProcessedEvent = timeStamp;
            setTemperatureCelsius(temperatureCelsius + celsiusTargetDelta);
        } else {
            Log.i(TAG, "dropping event");
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if(velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                }
                else {
                    velocityTracker.clear();
                }
                velocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                handleMoveEventWithVelocity(velocityTracker.getYVelocity(pointerId));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                velocityTracker.recycle();
                velocityTracker = null;
                break;
        }
        return true;
    }
}
