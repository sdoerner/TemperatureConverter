package de.sebastian_doerner.temperatureconverter;

import android.os.Bundle;

import de.sebastian_doerner.common.BaseConverterActivity;

public final class ConverterActivity extends BaseConverterActivity {

    private static final float VELOCITY_TO_TEMPERATURE_DELTA_RATIO = 700.0f;

    public ConverterActivity() {
        super(VELOCITY_TO_TEMPERATURE_DELTA_RATIO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        initialize();
    }

}
