package com.example.mini_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Thongbao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quytacduaxe);

        TextView rulesText = findViewById(R.id.rules_text);
        rulesText.setText("\"1. Nhấn nút Close để đóng thông báo.\n" +
                "          \"2. Xe sẽ tự động chạy trong làn của mình.\n" +
                "          \"3. Xe nào vượt qua vạch đích đầu tiên sẽ thắng.\n" +
                "          \"4. Bạn có thể thoát trò chơi bất kỳ lúc nào bằng cách nhấn nút Log Out.\n" +
                "          \"Quy tắc đặt cược:\n" +
                "          \"- Bạn có thể đặt cược vào nhiều đối tượng.\n" +
                "          \"- Nếu thắng, số tiền cược sẽ được gấp đôi.\n" +
                "          \"- Ví dụ: Cược 100.000 VNĐ, nếu thắng nhận lại 200.000 VNĐ.\";");

        Button closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thongbao.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}