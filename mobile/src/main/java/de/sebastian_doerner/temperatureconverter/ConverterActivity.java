package de.sebastian_doerner.temperatureconverter;

import static de.sebastian_doerner.common.TemperatureCalculator.getFahrenheitFromCelsius;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

import de.sebastian_doerner.common.BaseConverterActivity;

public final class ConverterActivity extends BaseConverterActivity {

    private static final String TAG = ConverterActivity.class.getSimpleName();

    private static final int INITIAL_TEMPERATURE_CELSIUS = 16;
    private static final int MIN_TEMPERATURE_CELSIUS = -273;
    private static final int MAX_TEMPERATURE_CELSIUS = 100;

    private static final float VELOCITY_TO_TEMPERATURE_DELTA_RATIO = 700.0f;
    private static final long MIN_TIME_BETWEEN_SMALL_EVENTS_MS = 200;

    float temperatureCelsius;

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

    private void setTemperatureCelsius(float temperatureCelsius) {
        temperatureCelsius =
                clamp(MIN_TEMPERATURE_CELSIUS, temperatureCelsius, MAX_TEMPERATURE_CELSIUS);
        this.temperatureCelsius = temperatureCelsius;

        setTemperatureOnView(celsiusView, R.string.celsius, temperatureCelsius);
        float temperatureFahrenheit = getFahrenheitFromCelsius(temperatureCelsius);
        setTemperatureOnView(fahrenheitView, R.string.fahrenheit, temperatureFahrenheit);
    }

    private void handleMoveEventWithVelocity(float velocity) {
        Log.i(TAG, "velocity: " + velocity);
        float magnitude = Math.abs(velocity / VELOCITY_TO_TEMPERATURE_DELTA_RATIO);
        float celsiusTargetDelta = magnitude * (velocity > 0 ? -1.0f : 1.0f);

        // For small magnitudes only process events ever so often to make it easier to make small
        // adjustments.
        long timeStamp = System.currentTimeMillis();
        if (magnitude > 0 &&
                timeStamp - lastProcessedEvent > MIN_TIME_BETWEEN_SMALL_EVENTS_MS / magnitude) {
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
