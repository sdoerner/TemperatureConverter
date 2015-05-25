package de.sebastian_doerner.temperatureconverter;

import android.os.Bundle;
import android.widget.TextView;

import de.sebastian_doerner.common.BaseConverterActivity;

public final class ConverterActivity extends BaseConverterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        initialize();
    }

}
