package com.example.vegeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vegeproject.barcode.barcode_fragment;
import com.example.vegeproject.news_and_guide.news_and_guide_fragment;
import com.example.vegeproject.search.search_fragment;
import com.example.vegeproject.setting.setting_fragment;

public class MainActivity extends AppCompatActivity {
    private search_fragment searchFragment;
    private barcode_fragment barcodeFragment;
    private news_and_guide_fragment newsAndGuideFragment;
    private setting_fragment settingFragment;

    private Button button1, button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View fragment = findViewById(R.id.fragment3);
        fragment.setVisibility(View.INVISIBLE);

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                searchFragment = new search_fragment();
                transactions.replace(R.id.container, searchFragment);
                transactions.addToBackStack(null);  //back키 누르면 다시 액티비티로 돌아오도록 설정
                transactions.commit();  //
            }
        });

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                barcodeFragment = new barcode_fragment();
                transactions.replace(R.id.container, barcodeFragment);
                transactions.addToBackStack(null);
                transactions.commit();
            }
        });

        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                newsAndGuideFragment = new news_and_guide_fragment();
                transactions.replace(R.id.container, newsAndGuideFragment);
                transactions.addToBackStack(null);
                transactions.commit();
            }
        });

        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                settingFragment = new setting_fragment();
                transactions.replace(R.id.container, settingFragment);
                transactions.addToBackStack(null);
                transactions.commit();
            }
        });


    }
}