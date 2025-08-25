package com.example.ultimatemusicplaylist;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent; // <-- added for MainActivity navigation
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Full immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.videoView);
        Button startButton = findViewById(R.id.startButton);
        appTitle = findViewById(R.id.appTitle);

        // Load video from res/raw/background.mp4
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background2);
        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(mp -> {
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            mp.setLooping(true);
            videoView.start();
        });

        // Animate title
        animateTitle();

        // ✅ Updated button click: go to MainActivity
        startButton.setOnClickListener(v -> {

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // close Splash so user can’t go back
        });
    }

    private void animateTitle() {
        ObjectAnimator slide = ObjectAnimator.ofFloat(appTitle, "translationY", -100f, 0f);
        ObjectAnimator fade = ObjectAnimator.ofFloat(appTitle, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(slide, fade);
        animatorSet.setDuration(1200);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Keep immersive mode and restart video
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        if (videoView != null) videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) videoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) videoView.stopPlayback();
    }
}

