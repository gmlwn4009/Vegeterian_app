package com.example.vegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vegeproject.barcode.BarcodeActivity;
import com.example.vegeproject.news_and_guide.news_and_guide_Activity;
import com.example.vegeproject.search.SearchActivity;
import com.example.vegeproject.setting.SettingActivity;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        button2 = (Button)findViewById(R.id.button2);//바코드
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(intent);
            }
        });

        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, news_and_guide_Activity.class);
                startActivity(intent);
            }
        });

        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


//        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
//        settingFragment = new setting_fragment();
//        transactions.replace(R.id.container, settingFragment);
//        transactions.addToBackStack(null);  //back키 누르면 다시 액티비티로 돌아오도록 설정
//        transactions.commit();
    }
}