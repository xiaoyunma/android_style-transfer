package com.example.vulcan.okhttp.mainMoudle;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ScrollView;

import com.example.vulcan.okhttp.R;

public class WelcomeActivity extends AppCompatActivity {

    ScrollView scrollView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
       /* scrollView = (ScrollView) findViewById(R.id.SCRVIEW_ID) ;
        scrollView.setVerticalScrollBarEnabled(false);*/

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1)
                    setContentView(R.layout.activity_main);
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                handler.sendEmptyMessage(1);
            }

        }).start();

    }
}