package com.example.danish.handyman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        String myData = getResources().getString(R.string.privacy_policy);
        WebView webView = findViewById(R.id.webView1);
        webView.loadData(myData, "text/html", "utf-8");
    }
}
