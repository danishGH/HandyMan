package com.example.danish.handyman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class TermsAndConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        String myData = getResources().getString(R.string.terms_conditions);
        WebView webView = findViewById(R.id.webView2);
        webView.loadData(myData, "text/html", "utf-8");
    }
}
