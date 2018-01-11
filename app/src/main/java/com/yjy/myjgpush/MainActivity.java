package com.yjy.myjgpush;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import junit.framework.Test;

public class MainActivity extends Activity {
    private final String ACTION_NAME = "name";

    private BroadcastReceiver mBroadcastReceiver = new MyReceiver();

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.main_btn);
        //注册广播
        registerBoradcastReceiver();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ACTION_NAME);
                mIntent.putExtra("name", "发送广播，相当于在这里传送数据");

                //发送广播
                sendBroadcast(mIntent);
                //GGaagit branch --set-upstream master origin/master
            }
        });
    }

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
}
