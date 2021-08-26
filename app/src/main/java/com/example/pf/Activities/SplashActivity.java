package com.example.pf.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pf.MainActivity;
import com.example.pf.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIMER = 800;
    ImageView mSplashImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen_layout);
        mSplashImg = findViewById(R.id.splashcatImg);

        Animation splashfade = new AlphaAnimation(1, 0);
        splashfade.setInterpolator(new AccelerateInterpolator());
        splashfade.setStartOffset(500);
        splashfade.setDuration(1800);

        mSplashImg.setAnimation(splashfade);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  //We do not have to open this again now
            }
        }, SPLASH_TIMER);
    }
}
