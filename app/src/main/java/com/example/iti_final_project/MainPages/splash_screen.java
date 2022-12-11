package com.example.iti_final_project.MainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.iti_final_project.R;

import java.util.Timer;
import java.util.TimerTask;

public class splash_screen extends AppCompatActivity {
    ProgressBar progressbar;
    ImageView img_splash;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        img_splash = findViewById(R.id.img_splash);
        img_splash.setScaleType(ImageView.ScaleType.FIT_XY);
        progressbar = findViewById(R.id.progressBar);
        progressbar.setScaleY(1.5f);
        int color = Color.parseColor("#81D4FA");
        progressbar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        progressbar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        Thread thread=new Thread(){
            @Override
            public void run(){
                try {
                    run_ProgressBar();
                    sleep(5000);
                    Intent intent=new Intent(getApplicationContext(),home.class);
                    startActivities(new Intent[]{intent});
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}};
        thread.start();
    }

    private void run_ProgressBar() {
        final Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter ++;
                progressbar.setProgress(counter);
                if(counter == 100)
                    timer.cancel();
            }
        };
        timer.schedule(timerTask, 0, 100);
    }
}