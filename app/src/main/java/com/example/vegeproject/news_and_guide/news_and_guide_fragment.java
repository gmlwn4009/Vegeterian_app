package com.example.vegeproject.news_and_guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vegeproject.R;
import com.google.android.material.tabs.TabLayout;

public class news_and_guide_fragment extends Fragment {

    private View newsFragment;
    private View guideFragment;
    private TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news_and_guide, container, false);

        tabLayout = root.findViewById(R.id.tab_layout);
        newsFragment = root.findViewById(R.id.newsFragment);
        guideFragment = root.findViewById(R.id.guideFragment);

        newsFragment.setVisibility(View.VISIBLE);
        guideFragment.setVisibility(View.INVISIBLE);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
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

        return root;
    }
}
