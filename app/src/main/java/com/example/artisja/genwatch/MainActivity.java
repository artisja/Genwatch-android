package com.example.artisja.genwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView clockInText;
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_TIME;
    LocalDateTime localTime;
    BroadcastReceiver broadcastReceiver;

    class UpdateTime extends TimerTask{
        @Override
        public void run() {
            updateTime(clockInText);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpButton();
        Timer timer = new Timer();
        timer.schedule(new UpdateTime(),0,1000);
    }

    private void setUpButton() {
        clockInText = findViewById(R.id.clock_in_text);
        updateTime(clockInText);
    }

    private void updateTime(TextView clockInText) {
        localTime = LocalDateTime.now();
        String currentTime = localTime.format(dateTimeFormatter);
        currentTime = currentTime.substring(0,currentTime.lastIndexOf(':'));
        clockInText.setText(currentTime);
    }

    @Override
    public void onStart() {
        super.onStart();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    updateTime(clockInText);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }
}
