package de.sebastian_doerner.temperatureconverter;

import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import de.sebastian_doerner.common.BaseConverterActivity;

public final class ConverterActivity extends BaseConverterActivity {

    private static final float VELOCITY_TO_TEMPERATURE_DELTA_RATIO = 200.0f;

    public ConverterActivity() {
        super(VELOCITY_TO_TEMPERATURE_DELTA_RATIO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstub);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
               initialize();
            }


        });
    }
}
