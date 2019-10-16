package com.khusainov.rinat.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private FinanceProgressView mFinanceProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFinanceProgressView = findViewById(R.id.finance_progress);
        final SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setMax(FinanceProgressView.MAX_PROGRESS);
        seekBar.setOnSeekBarChangeListener(new BaseOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mFinanceProgressView.setProgress(progress);
            }
        });
        seekBar.setProgress(42);
    }


    private static abstract class BaseOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}

