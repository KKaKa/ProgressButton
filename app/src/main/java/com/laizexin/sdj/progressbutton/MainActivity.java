package com.laizexin.sdj.progressbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laizexin.sdj.library.ProgressButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressButton progressButton = findViewById(R.id.progress_button);

        progressButton.setProgress(0);
        progressButton.setOnClickListener(v -> {
            if (progressButton.getProgress() == 0) {
                progressButton.setProgress(50);
            } else if (progressButton.getProgress() == 100) {
                progressButton.setProgress(0);
            } else {
                progressButton.setProgress(100);
            }
        });

        ProgressButton progressButton2 = findViewById(R.id.progress_button2);
        progressButton2.setProgress(0);
        progressButton2.setOnClickListener(v -> {
            if (progressButton2.getProgress() == 0) {
                progressButton2.setProgress(50);
            } else if (progressButton2.getProgress() == 50) {
                progressButton2.setProgress(-1);
            } else {
                progressButton2.setProgress(50);
            }
        });

        ProgressButton progressButton3 = findViewById(R.id.progress_button3);
        progressButton3.setProgress(0);
        progressButton3.setOnClickListener(v -> {
            if (progressButton3.getProgress() == 0) {
                progressButton3.setProgress(50);
            } else if (progressButton3.getProgress() == 100) {
                progressButton3.setProgress(0);
            } else {
                progressButton3.setProgress(100);
            }
        });

        ProgressButton progressButton4 = findViewById(R.id.progress_button4);
        progressButton4.setProgress(0);
        progressButton4.setOnClickListener(v -> {
            if (progressButton4.getProgress() == 0) {
                progressButton4.setProgress(50);
            } else if (progressButton4.getProgress() == 50) {
                progressButton4.setProgress(-1);
            } else {
                progressButton4.setProgress(50);
            }
        });
    }
}
