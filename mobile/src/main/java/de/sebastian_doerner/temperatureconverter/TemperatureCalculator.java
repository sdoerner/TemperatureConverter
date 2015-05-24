package de.sebastian_doerner.temperatureconverter;

/**
 * Class to convert between Celsius and Fahrenheit temperatures.
 */
public final class TemperatureCalculator {
    private TemperatureCalculator() {}

    public static float getCelsiusFromFahrenheit(float fahrenheit) {
        return (fahrenheit - 32) * 5.0f / 9.0f;
    }

    public static float getFahrenheitFromCelsius(float celsius) {
        return (celsius * 9.0f / 5.0f) + 32.0f;
    }
}
