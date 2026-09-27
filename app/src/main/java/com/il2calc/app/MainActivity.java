package com.il2calc.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(12, 16, 19));
        getWindow().setNavigationBarColor(Color.rgb(12, 16, 19));

        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(12, 16, 19));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());
        setContentView(webView);

        StringBuilder html = new StringBuilder();
        try {
            for (int i = 1; i <= 6; i++) {
                String file = String.format("part%02d.txt", i);
                BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(file), "UTF-8"));
                char[] buffer = new char[8192];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    html.append(buffer, 0, read);
                }
                reader.close();
            }
            webView.loadDataWithBaseURL("file:///android_asset/", html.toString(), "text/html", "UTF-8", null);
        } catch (Exception e) {
            webView.loadData("<h2 style='color:white;background:#111;padding:20px'>Could not load app.</h2>", "text/html", "UTF-8");
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
