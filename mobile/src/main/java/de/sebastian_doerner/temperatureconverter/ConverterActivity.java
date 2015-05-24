package de.sebastian_doerner.temperatureconverter;

import static de.sebastian_doerner.temperatureconverter.TemperatureCalculator.getFahrenheitFromCelsius;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;



public final class ConverterActivity extends Activity {

    private static final int INITIAL_TEMPERATURE_CELSIUS = 16;
    private static final int MIN_TEMPERATURE_CELSIUS = -273;
    private static final int MAX_TEMPERATURE_CELSIUS = 100;

    int temperatureCelsius;

    private TextView celsiusView;
    private TextView fahrenheitView;

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
}
