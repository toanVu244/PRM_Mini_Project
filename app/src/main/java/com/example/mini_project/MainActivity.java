package com.example.mini_project;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBarLine1, seekBarLine2, seekBarLine3, seekBarLine4, seekBarLine5;
    Button btnStart, btnReStart;
    Switch switchMusic;

    int spdLine1, spdLine2, spdLine3, spdLine4, spdLine5;
    boolean raceFinished = false; // Để kiểm soát khi một cuộc đua kết thúc

    Handler handler = new Handler(); // Sử dụng handler để điều khiển các runnable

    // MediaPlayer cho âm thanh
    MediaPlayer startSound, endSound, backgroundMusic, clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // Khởi tạo giao diện
        seekBarLine1 = findViewById(R.id.seekBarLine1);
        seekBarLine2 = findViewById(R.id.seekBarLine2);
        seekBarLine3 = findViewById(R.id.seekBarLine3);
        seekBarLine4 = findViewById(R.id.seekBarLine4);
        seekBarLine5 = findViewById(R.id.seekBarLine5);
        btnStart = findViewById(R.id.buttonStart);
        btnReStart = findViewById(R.id.buttonRestart);
        switchMusic = findViewById(R.id.switchMusic); // Thêm switch bật/tắt nhạc

        // Khởi tạo các đối tượng MediaPlayer với các file âm thanh
        startSound = MediaPlayer.create(this, R.raw.nen);
        endSound = MediaPlayer.create(this, R.raw.nen);
        clickSound = MediaPlayer.create(this, R.raw.nen);
        backgroundMusic = MediaPlayer.create(this, R.raw.nen);
        backgroundMusic.setLooping(true); // Nhạc nền sẽ lặp lại

        // Bắt đầu phát nhạc nền
        backgroundMusic.start();

        // Thiết lập Switch để bật/tắt nhạc nền
        switchMusic.setChecked(true); // Mặc định bật nhạc nền
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!backgroundMusic.isPlaying()) {
                    backgroundMusic.start(); // Bật nhạc nền
                }
            } else {
                if (backgroundMusic.isPlaying()) {
                    backgroundMusic.pause(); // Tắt nhạc nền
                }
            }
        });

        btnStart.setOnClickListener(view -> {
            if (raceFinished) return; // Không cho phép nhấn Start khi cuộc đua đã kết thúc
            //clickSound.start(); // Phát âm thanh khi nhấn nút
            //startSound.start(); // Phát âm thanh bắt đầu đua
            setUpSpeed(); // Thiết lập tốc độ ngẫu nhiên

            // Tạo Runnable cho từng SeekBar với tốc độ khác nhau
            startRace();
        });

        btnReStart.setOnClickListener(view -> {
            //clickSound.start(); // Phát âm thanh khi nhấn nút Restart
            resetRace(); // Đặt lại vị trí SeekBar về 0 khi ấn nút "Đặt lại"
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Bắt đầu cuộc đua
    public void startRace() {
        raceFinished = false;
        createRunnable(seekBarLine1, spdLine1, "Line 1");
        createRunnable(seekBarLine2, spdLine2, "Line 2");
        createRunnable(seekBarLine3, spdLine3, "Line 3");
        createRunnable(seekBarLine4, spdLine4, "Line 4");
        createRunnable(seekBarLine5, spdLine5, "Line 5");
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
                        if (!raceFinished) { // Nếu cuộc đua chưa kết thúc thì kết thúc tại đây
                            raceFinished = true;
                            Toast.makeText(MainActivity.this, lineName + " wins!", Toast.LENGTH_SHORT).show();
                            endSound.start(); // Phát âm thanh kết thúc
                        }
                    } else {
                        seekBar.setProgress(progress);
                        handler.postDelayed(this, 100); // Lặp lại sau 100ms để điều khiển tốc độ
                    }
                }
            }
        };
        handler.post(runnable); // Bắt đầu Runnable
    }

    // Tạo tốc độ ngẫu nhiên cho từng đường đua
    public int createRandomSpeed() {
        Random random = new Random();
        return random.nextInt(10) + 1; // Trả về số ngẫu nhiên từ 1 đến 10
    }

    // Thiết lập tốc độ ngẫu nhiên cho các đường đua
    public void setUpSpeed() {
        spdLine1 = createRandomSpeed();
        spdLine2 = createRandomSpeed();
        spdLine3 = createRandomSpeed();
        spdLine4 = createRandomSpeed();
        spdLine5 = createRandomSpeed();
    }

    // Đặt lại vị trí các thanh SeekBar
    public void resetRace() {
        raceFinished = false;
        seekBarLine1.setProgress(0);
        seekBarLine2.setProgress(0);
        seekBarLine3.setProgress(0);
        seekBarLine4.setProgress(0);
        seekBarLine5.setProgress(0);
        handler.removeCallbacksAndMessages(null); // Dừng tất cả các luồng đang chạy
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng các tài nguyên âm thanh
        if (backgroundMusic != null) {
            backgroundMusic.release();
            backgroundMusic = null;
        }
        if (startSound != null) {
            startSound.release();
            startSound = null;
        }
        if (endSound != null) {
            endSound.release();
            endSound = null;
        }
        if (clickSound != null) {
            clickSound.release();
            clickSound = null;
        }
    }
}
