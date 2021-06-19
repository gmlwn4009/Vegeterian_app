package com.example.vegeproject.news_and_guide;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.R;
import com.google.android.material.tabs.TabLayout;

public class news_and_guide_Activity extends AppCompatActivity {

    private View newsFragment;
    private View guideFragment;
    private TabLayout tabLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_news_and_guide);

        tabLayout = findViewById(R.id.tab_layout);
        newsFragment = findViewById(R.id.newsFragment);
        guideFragment = findViewById(R.id.guideFragment);

        newsFragment.setVisibility(View.VISIBLE);
        guideFragment.setVisibility(View.INVISIBLE);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                //첫번쨰 tab 선택 시 뉴스 화면, 두번째 tab 선택 시 가이드 화면
                if(pos == 0){
                    newsFragment.setVisibility(View.VISIBLE);
                    guideFragment.setVisibility(View.INVISIBLE);
                }
                else{
                    newsFragment.setVisibility(View.INVISIBLE);
                    guideFragment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
