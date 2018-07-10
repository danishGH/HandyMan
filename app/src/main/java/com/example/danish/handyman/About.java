package com.example.danish.handyman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String myData = getResources().getString(R.string.about);
        WebView webView = findViewById(R.id.webView3);
        webView.loadData(myData, "text/html", "utf-8");
    }
}
