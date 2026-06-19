package com.karaoketv.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Force full fullscreen immersion mode hiding system clock status panels
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.karaokeWebView);
        WebSettings webSettings = myWebView.getSettings();
        
        // Critical settings adjustments for embedding YouTube frameworks
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false); // Bypasses video block rules
        webSettings.setAllowFileAccess(true);

        myWebView.setWebViewClient(new WebViewClient());
        
        // Intercepts and grants browser microphone requests natively within the APK container
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }
        });

        // Points to your hosted web setup or your local compilation server URL address
        myWebView.loadUrl("https://mobile-tv-karaoke.yourusername.repl.co");
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack(); // Keeps logo click navigation functional
        } else {
            super.onBackPressed();
        }
    }
}
