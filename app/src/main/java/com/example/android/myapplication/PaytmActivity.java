package com.example.android.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.myapplication.ChatApp.AnonymousChat;

public class PaytmActivity extends AppCompatActivity {

    private WebView chatWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
        chatWindow = findViewById(R.id.chatWindow);

        WebSettings webSettings = chatWindow.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(PaytmActivity.this);
        chatWindow.setWebViewClient(webViewClient);
        chatWindow.loadUrl("https://paytm.com/");

    }
        public class WebViewClientImpl extends WebViewClient {
            private Activity activity = null;

            public WebViewClientImpl(PaytmActivity activity) {
                this.activity = activity;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.indexOf("https://paytm.com/") > -1) return false;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(intent);
                return true;
            }
        }



}
