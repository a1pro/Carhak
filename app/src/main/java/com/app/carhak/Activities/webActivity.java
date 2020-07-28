package com.app.carhak.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.carhak.R;

public class webActivity extends AppCompatActivity {
    private TextView title_tv;
    private WebView webview;
    private ImageView imageOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webview = findViewById(R.id.webview);
        title_tv=findViewById(R.id.title_tv);
        imageOpen=findViewById(R.id.imageOpen);

        String url = getIntent().getStringExtra("url");

        String title=getIntent().getStringExtra("title");


        if (title!=null){
            title_tv.setText(title);
        }

        if (url != null) {
            webview = findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setPluginState(WebSettings.PluginState.ON);
            webview.loadUrl(url);
        }

        imageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}