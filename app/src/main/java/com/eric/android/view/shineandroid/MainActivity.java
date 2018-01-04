package com.eric.android.view.shineandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eric.android.view.shine.ShineFrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ShineFrameLayout fl_shine = findViewById(R.id.fl_shine);
        fl_shine.setTilt(110);
        fl_shine.setAngle(ShineFrameLayout.MaskAngle.CW_180);
        fl_shine.startShimmerAnimation();
        fl_shine.setOnClickListener(v -> {
            if (fl_shine.isAnimationStarted())
                fl_shine.stopShimmerAnimation();
            else
                fl_shine.startShimmerAnimation();
        });
    }
}
