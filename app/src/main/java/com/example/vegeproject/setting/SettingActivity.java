package com.example.vegeproject.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.MainActivity;
import com.example.vegeproject.R;
import com.example.vegeproject.search.SearchActivity;
import com.example.vegeproject.setting.SettingLevel;

public class SettingActivity extends AppCompatActivity {
    private TextView setLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setLevel = (TextView) findViewById(R.id.setLev);

        setLevel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SettingActivity.this, SettingLevel.class);
                startActivity(intent);
            }
        });

    }
}
