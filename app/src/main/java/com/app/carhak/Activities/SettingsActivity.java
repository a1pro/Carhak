package com.app.carhak.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.carhak.R;

public class SettingsActivity extends AppCompatActivity {
private ImageView imageOpen;
private TextView tv_faq,tv_help,tv_support,tv_privacy,tv_terms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        imageOpen=findViewById(R.id.imageOpen);
        tv_faq=findViewById(R.id.tv_faq);
        tv_help=findViewById(R.id.tv_help);
        tv_support=findViewById(R.id.tv_support);
        tv_privacy=findViewById(R.id.tv_privacy);
        tv_terms=findViewById(R.id.tv_terms);

        tv_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,webActivity.class);
                intent.putExtra("url","https://carhak.com/faq");
                intent.putExtra("title","FAQ");
                startActivity(intent);
            }
        });
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,webActivity.class);
                intent.putExtra("url","https://carhak.com/help");
                intent.putExtra("title","Help");
                startActivity(intent);
            }
        });
        tv_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,webActivity.class);
                intent.putExtra("url","https://carhak.com/support");
                intent.putExtra("title","Support");
                startActivity(intent);
            }
        });
        tv_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,webActivity.class);
                intent.putExtra("url","https://carhak.com/privacy-policy");
                intent.putExtra("title","Privacy Policy");
                startActivity(intent);
            }
        });
        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,webActivity.class);
                intent.putExtra("url","https://carhak.com/terms-conditions");
                intent.putExtra("title","Terms & Condition");
                startActivity(intent);
            }
        });

        imageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}