package com.example.android.myapplication.ChatApp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.myapplication.R;

public class AnonymousChat extends AppCompatActivity implements View.OnClickListener{

    private Button btnQuit;
    private WebView chatWindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anonymous_chat);
        btnQuit = findViewById(R.id.btnQuit);
        chatWindow = findViewById(R.id.chatWindow);

        WebSettings webSettings = chatWindow.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(AnonymousChat.this);
        chatWindow.setWebViewClient(webViewClient);
        chatWindow.loadUrl("http://realchatapp.herokuapp.com/");

        btnQuit.setOnClickListener(this);

    }

    public class WebViewClientImpl extends WebViewClient
    {

        private Activity activity = null;

        public WebViewClientImpl(AnonymousChat activity) {
            this.activity = activity;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if(url.indexOf("http://realchatapp.herokuapp.com/") > -1 ) return false;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
            return true;
        }

    }
    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Leaving Chat room", Toast.LENGTH_SHORT).show();
       // chatWindow.getSettings().setLoadsImagesAutomatically(true);
        chatWindow.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        chatWindow.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        chatWindow.loadUrl("http://realchatapp.herokuapp.com/");
    }


}
