package com.example.mini_project;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBarLine1;
    SeekBar seekBarLine2;
    SeekBar seekBarLine3;
    SeekBar seekBarLine4;
    SeekBar seekBarLine5;
    Button btnStart;
    Button btnReStart;

    int spdLine1, spdLine2, spdLine3, spdLine4, spdLine5;
    boolean raceFinished = false;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        seekBarLine1 = findViewById(R.id.seekBarLine1);
        seekBarLine2 = findViewById(R.id.seekBarLine2);
        seekBarLine3 = findViewById(R.id.seekBarLine3);
        seekBarLine4 = findViewById(R.id.seekBarLine4);
        seekBarLine5 = findViewById(R.id.seekBarLine5);
        btnStart = findViewById(R.id.buttonStart);
        btnReStart = findViewById(R.id.buttonRestart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpSpeed();

                // Tạo Runnable cho từng SeekBar với tốc độ khác nhau
                createRunnable(seekBarLine1, spdLine1, "Line 1");
                createRunnable(seekBarLine2, spdLine2, "Line 2");
                createRunnable(seekBarLine3, spdLine3, "Line 3");
                createRunnable(seekBarLine4, spdLine4, "Line 4");
                createRunnable(seekBarLine5, spdLine5, "Line 5");
            }
        });

        btnReStart.setOnClickListener(view -> {
            resetRace(); // Đặt lại vị trí SeekBar về 0 khi ấn nút "Đặt lại"
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void createRunnable(SeekBar seekBar, int spd, String lineName) {
        Runnable runnable = new Runnable() {
            int progress = 0;

            @Override
            public void run() {
                if (!raceFinished) {
                    progress += spd;
                    if (progress >= 100) {
                        progress = 100;
                        seekBar.setProgress(progress);
                        if (!raceFinished) {
                            raceFinished = true;
                            Toast.makeText(MainActivity.this, lineName + " wins!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        seekBar.setProgress(progress);
                        handler.postDelayed(this, 1000);
                    }
                }
            }
        };
        handler.post(runnable); // Bắt đầu Runnable
    }

    public int createRandomSpeed() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    public void setUpSpeed() {
        spdLine1 = createRandomSpeed();
        spdLine2 = createRandomSpeed();
        spdLine3 = createRandomSpeed();
        spdLine4 = createRandomSpeed();
        spdLine5 = createRandomSpeed();
    }

    public void resetRace() {
        raceFinished = false;
        seekBarLine1.setProgress(0);
        seekBarLine2.setProgress(0);
        seekBarLine3.setProgress(0);
        seekBarLine4.setProgress(0);
        seekBarLine5.setProgress(0);
        handler.removeCallbacksAndMessages(null);
    }
}
