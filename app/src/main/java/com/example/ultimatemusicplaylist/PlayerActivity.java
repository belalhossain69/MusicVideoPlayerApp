package com.example.ultimatemusicplaylist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private WebView videoWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        videoWebView = findViewById(R.id.videoWebView);

        // Get the video URL from intent
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        if (videoUrl != null) {
            // 🔹 Check if URL is an embed link
            if (videoUrl.contains("/embed/")) {
                // Use WebView for embed links
                WebSettings webSettings = videoWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                videoWebView.setWebViewClient(new WebViewClient());
                videoWebView.loadUrl(videoUrl);
            } else {
                // Use Intent for normal YouTube links (official music)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                startActivity(intent);
                finish(); // Close this activity after opening YouTube app
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (videoWebView.canGoBack()) {
            videoWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

