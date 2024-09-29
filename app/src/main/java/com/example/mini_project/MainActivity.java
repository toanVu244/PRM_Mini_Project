package com.example.mini_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBarLine1, seekBarLine2, seekBarLine3, seekBarLine4, seekBarLine5;
    Button btnStart, btnReStart, btnHistory;
    CheckBox cbLine1, cbLine2, cbLine3, cbLine4, cbLine5;
    boolean choiceLine1 = false, choiceLine2 = false, choiceLine3 = false, choiceLine4 = false, choiceLine5 = false;
    boolean restarted = true, raceFinished = false;
    int money = 1000, totalBet = 0, spdLine1, spdLine2, spdLine3, spdLine4, spdLine5;
    TextView tvMoney;
    ArrayList<Integer> chosenLines = new ArrayList<>();
    Handler handler = new Handler();
    MediaPlayer startSound, endSound, backgroundMusic, clickSound;

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
        btnHistory = findViewById(R.id.buttonHistory);
        cbLine1 = findViewById(R.id.checkBoxLine1);
        cbLine2 = findViewById(R.id.checkBoxLine2);
        cbLine3 = findViewById(R.id.checkBoxLine3);
        cbLine4 = findViewById(R.id.checkBoxLine4);
        cbLine5 = findViewById(R.id.checkBoxLine5);
        tvMoney = findViewById(R.id.textViewMoney);

        Toast.makeText(this, "Chúc mừng bạn nhận: " + money + " đồng từ hệ thống", Toast.LENGTH_SHORT).show();
        tvMoney.setText("1000");

        cbLine1.setOnCheckedChangeListener((buttonView, isChecked) -> choiceLine1 = isChecked);
        cbLine2.setOnCheckedChangeListener((buttonView, isChecked) -> choiceLine2 = isChecked);
        cbLine3.setOnCheckedChangeListener((buttonView, isChecked) -> choiceLine3 = isChecked);
        cbLine4.setOnCheckedChangeListener((buttonView, isChecked) -> choiceLine4 = isChecked);
        cbLine5.setOnCheckedChangeListener((buttonView, isChecked) -> choiceLine5 = isChecked);

        Switch switchMusic = findViewById(R.id.switchMusic);
        startSound = MediaPlayer.create(this, R.raw.nen);
        endSound = MediaPlayer.create(this, R.raw.nen);
        clickSound = MediaPlayer.create(this, R.raw.nen);
        backgroundMusic = MediaPlayer.create(this, R.raw.nen);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        switchMusic.setChecked(true);
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) backgroundMusic.start();
            else backgroundMusic.pause();
        });

        btnStart.setOnClickListener(view -> {
            if (!raceFinished && gambit()) {
                clickSound.start();
                startSound.start();
                setUpSpeed();
                startRace();
            }
        });

        btnReStart.setOnClickListener(view -> resetRace());

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Histories.class);
                startActivity(intent);
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void startRace() {
        raceFinished = false;
        createRunnable(seekBarLine1, spdLine1, 1);
        createRunnable(seekBarLine2, spdLine2, 2);
        createRunnable(seekBarLine3, spdLine3, 3);
        createRunnable(seekBarLine4, spdLine4, 4);
        createRunnable(seekBarLine5, spdLine5, 5);
    }

    public void createRunnable(SeekBar seekBar, int spd, int lineName) {
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
                            checkWinner(lineName);
                            endSound.start();
                            raceFinished = true;
                        }
                    } else {
                        seekBar.setProgress(progress);
                        handler.postDelayed(this, 1000);
                    }
                }
            }
        };
        handler.post(runnable);
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
        restarted = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public boolean gambit() {
        if (restarted) {
            if (money > 0) {
                totalBet = 0;
                chosenLines.clear();
                if (choiceLine1) {
                    totalBet += 10;
                    chosenLines.add(1);
                }
                if (choiceLine2) {
                    totalBet += 10;
                    chosenLines.add(2);
                }
                if (choiceLine3) {
                    totalBet += 10;
                    chosenLines.add(3);
                }
                if (choiceLine4) {
                    totalBet += 10;
                    chosenLines.add(4);
                }
                if (choiceLine5) {
                    totalBet += 20;
                    chosenLines.add(5);
                }

                if (chosenLines.size() > 0) {
                    if (totalBet <= money) {
                        tvMoney.setText(String.valueOf(money - totalBet));
                        money -= totalBet;
                        Toast.makeText(this, "Đã đặt cược: " + totalBet + " đồng", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        Toast.makeText(this, "Không đủ tiền để đặt cược!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Hãy chọn ít nhất 1 dòng để đặt cược!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(this, "Không có tiền để đặt cược!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Vui lòng Restart để bắt đầu!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void checkWinner(int winningLine) {
        Toast.makeText(MainActivity.this, "Line " + winningLine + " wins!", Toast.LENGTH_SHORT).show();
        int winnings = 0;

        if (chosenLines.contains(winningLine)) {
            if (winningLine == 5) {
                winnings += 20 * 2;
            } else {
                winnings += 10 * 2;
            }
        }

        if (winnings > 0) {
            money += winnings;
            Toast.makeText(MainActivity.this, "Bạn đã thắng! Nhận được " + winnings + " đồng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Bạn đã thua cược!", Toast.LENGTH_SHORT).show();
        }
        tvMoney.setText(String.valueOf(money));
        History history = new History(totalBet, winnings, winningLine);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", null);
        JsonUntils.addHistoryToAccount(MainActivity.this, savedUsername, history);

    }
}
