package com.example.mini_project;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBarLine1;
    SeekBar seekBarLine2;
    SeekBar seekBarLine3;
    SeekBar seekBarLine4;
    SeekBar seekBarLine5;
    Button btnStart;
    Button btnReStart;
    CheckBox cbLine1;
    CheckBox cbLine2;
    CheckBox cbLine3;
    CheckBox cbLine4;
    CheckBox cbLine5;
    boolean choiceLine1 = false;
    boolean choiceLine2 = false;
    boolean choiceLine3 = false;
    boolean choiceLine4 = false;
    boolean choiceLine5 = false;
    boolean restarted = true;
    int money = 1000;
    TextView tvMoney;
    int totalBet = 0;
    int spdLine1, spdLine2, spdLine3, spdLine4, spdLine5;
    boolean raceFinished = false;
    ArrayList<Integer> chosenLines = new ArrayList<>();
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
        cbLine1 = findViewById(R.id.checkBoxLine1);
        cbLine2 = findViewById(R.id.checkBoxLine2);
        cbLine3 = findViewById(R.id.checkBoxLine3);
        cbLine4 = findViewById(R.id.checkBoxLine4);
        cbLine5 = findViewById(R.id.checkBoxLine5);
        tvMoney = findViewById(R.id.textViewMoney);

        Toast.makeText(this, "Chúc mừng bạn nhận: " + money + " đồng từ hệ thống", Toast.LENGTH_SHORT).show();
        tvMoney.setText("1000");

        cbLine1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceLine1 = isChecked;
            }
        });

        cbLine2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceLine2 = isChecked;
            }
        });

        cbLine3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceLine3 = isChecked;
            }
        });

        cbLine4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceLine4 = isChecked;
            }
        });

        cbLine5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choiceLine5 = isChecked;
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gambit()) {
                    setUpSpeed();
                    createRunnable(seekBarLine1, spdLine1, 1);
                    createRunnable(seekBarLine2, spdLine2, 2);
                    createRunnable(seekBarLine3, spdLine3, 3);
                    createRunnable(seekBarLine4, spdLine4, 4);
                    createRunnable(seekBarLine5, spdLine5, 5);
                    restarted = false;
                }
            }
        });

        btnReStart.setOnClickListener(view -> {
            resetRace();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
    }

}
