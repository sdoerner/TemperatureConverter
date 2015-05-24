package de.sebastian_doerner.common;

import android.app.Activity;
import android.widget.TextView;

public abstract class BaseConverterActivity extends Activity {

    protected void setTemperatureOnView(TextView textView, int formatString, float temperature) {
        int roundedTemperature = Math.round(temperature);
        String temperatureString = getString(formatString, roundedTemperature);
        textView.setText(temperatureString);
    }

    protected float clamp(float minValue, float targetValue, float maxValue) {
        return Math.max(Math.min(targetValue, maxValue), minValue);
    }
}
