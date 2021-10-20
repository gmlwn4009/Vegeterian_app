package com.example.vegeproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.vegeproject.barcode.BarcodeActivity;
import com.example.vegeproject.news_and_guide.news_and_guide_Activity;
import com.example.vegeproject.search.SearchActivity;
import com.example.vegeproject.setting.SettingActivity;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout btnSearch, btnBarcode, btnGuide, btnSetting;
    View.OnTouchListener touchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        btnBarcode = findViewById(R.id.btnBarcode);
        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(intent);
            }
        });

        btnGuide = findViewById(R.id.btnGuide);
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, news_and_guide_Activity.class);
                startActivity(intent);
            }
        });

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //버튼 클릭 효과
        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ConstraintLayout image = (ConstraintLayout) v;
                float curX = event.getX();
                float curY = event.getY();

                switch (event.getAction()) {
                    //손가락 눌림
                    case MotionEvent.ACTION_DOWN: {
                        image.setAlpha((float) 0.8);
                        return false;
                    }
                    //손가락 뗌
                    case MotionEvent.ACTION_UP: {
                        image.setAlpha(1);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        };
        btnSearch.setOnTouchListener(touchListener);
        btnBarcode.setOnTouchListener(touchListener);
        btnGuide.setOnTouchListener(touchListener);
        btnSearch.setOnTouchListener(touchListener);
    }
}