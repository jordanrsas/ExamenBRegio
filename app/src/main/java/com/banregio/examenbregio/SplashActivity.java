package com.banregio.examenbregio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.banregio.examenbregio.activities.LoginActivity;
import com.banregio.examenbregio.utils.SharedPrefsManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrefsManager.initialize(this);
        setContentView(R.layout.activity_splash);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                /*Intent intent;
                if (SharedPrefsManager.getInstance().getBoolean(SharedPreferencesKeys.IS_LOGGED)) {
                    intent = new Intent(SplashActivity.this, UserActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }*/
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 2000);
    }
}
