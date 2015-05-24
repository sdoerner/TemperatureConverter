package de.sebastian_doerner.temperatureconverter;

/**
 * Class to convert between Celsius and Fahrenheit temperatures.
 */
public final class TemperatureCalculator {
    private TemperatureCalculator() {}

    public static int getCelsiusFromFahrenheit(int fahrenheit) {
        float exactResult =  (fahrenheit - 32) * 5.0f / 9.0f;
        return Math.round(exactResult);
    }

    public static int getFahrenheitFromCelsius(int celsius) {
        float exactResult = (celsius * 9.0f / 5.0f) + 32.0f;
        return Math.round(exactResult);
    }
}
