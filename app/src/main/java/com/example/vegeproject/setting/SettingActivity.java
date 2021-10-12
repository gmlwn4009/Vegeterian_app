package com.example.vegeproject.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.MainActivity;
import com.example.vegeproject.R;
import com.example.vegeproject.search.SearchActivity;
import com.example.vegeproject.setting.SettingLevel;

public class SettingActivity extends AppCompatActivity {

    private TextView setLevel,personal_lev;
    SharedPreferences pref;//프리퍼런스
    SharedPreferences.Editor editor;//에디터
    String pref_str;//저장될 장소

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setLevel = (TextView) findViewById(R.id.setLev);

        //Shared Preference 초기화
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        //저장해둔 값 불러오기
        pref_str = pref.getString("Pref_str","단계를 설정해주세요:)");

        //레이아웃 변수 초기화 및 앱을 켜면 이전에 저장해둔 값 표시해주기
        personal_lev = (TextView) findViewById(R.id.level_present);
        if(pref_str.equals("단계를 설정해주세요:)"))
           personal_lev.setText(pref_str);

        else
            personal_lev.setText("당신은 " + pref_str + "이군요!");

        setLevel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SettingActivity.this, SettingLevel.class);
                startActivityForResult(intent,1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        personal_lev = (TextView) findViewById(R.id.level_present);

        if (requestCode == 1000)
        {

                if (resultCode == Activity.RESULT_OK) {
                    String sendText = data.getExtras().getString("결과");
                    personal_lev.setText("당신은 " + sendText + "이군요!");
                    Log.e("sendText", sendText);
                    pref_str = data.getExtras().getString("결과");
                    editor.putString("Pref_str", pref_str);
                    editor.apply();
                }
                else if(resultCode == RESULT_CANCELED) {
                    return;
                }
                else{

                }


        }


    }

}
