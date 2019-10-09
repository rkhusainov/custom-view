package com.khusainov.rinat.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FinanceProgressView financeProgressView = findViewById(R.id.finance_progress);
        financeProgressView.setProgress(75);

    }
}
