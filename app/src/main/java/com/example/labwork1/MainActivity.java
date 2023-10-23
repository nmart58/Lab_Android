package com.example.labwork1;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.Nullable;


public class MainActivity extends Activity {
    public int count1 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedIBundle) {
        super.onCreate(savedIBundle);
        setContentView(R.layout.activity_helloact);

        Button button1 = findViewById(R.id.button);
        Button button2 =  findViewById(R.id.button2);
        TextView textView = findViewById(R.id.textView2);
        FrameLayout frameLayout = findViewById(R.id.frame1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (count1) {
                    case 1:
                        count1 += 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!" );
                        break;
                    case 2:
                        count1 += 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!" );
                        break;
                    case 3:
                        count1 += 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!" );
                        break;
                    case 4:
                        count1 += 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.purple));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!" );
                        break;
                    case 5:
                        count1 += 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.gold));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!" );
                        break;
                    default:
                        count1 += 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!" );
                        break;
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (count1) {
                    case 1:
                        count1 -= 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!");
                        break;
                    case 2:
                        count1 -= 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!");
                        break;
                    case 3:
                        count1 -= 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!");
                        break;
                    case 4:
                        count1 -= 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.purple));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!");
                        break;
                    case 5:
                        count1 -= 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.gold));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!");
                        break;
                    default:
                        count1 -= 1;
                        frameLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        textView.setText("Ваш текущий счет: " + count1 + " баллов!");
                        break;
                }
            }
        });
    }
}
