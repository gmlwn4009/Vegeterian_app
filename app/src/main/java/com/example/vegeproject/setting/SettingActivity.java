package com.example.vegeproject.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.vegeproject.MainActivity;
import com.example.vegeproject.R;
import com.example.vegeproject.search.SearchActivity;
import com.example.vegeproject.setting.SettingLevel;
import com.example.vegeproject.setting.SettingNickname;

public class SettingActivity extends AppCompatActivity {

    private TextView setLevel,personal_lev,setNickname,personal_nick;
    private ImageView personal_lev_img;
    View.OnTouchListener touchListener;
    SharedPreferences pref;//프리퍼런스
    SharedPreferences.Editor editor;//에디터
    String pref_str,pref_str2;//저장될 장소

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setLevel = (TextView) findViewById(R.id.setLev);
        setNickname = (TextView) findViewById(R.id.change_nick);

        //Shared Preference 초기화
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        pref = getSharedPreferences("prefnick",Activity.MODE_PRIVATE);
        editor = pref.edit();

        //저장해둔 값 불러오기
        pref_str = pref.getString("Pref_str","단계를 설정해주세요:)");
        pref_str2 = pref.getString("Pref_nick","비거닝님, 안녕하세요.");

        //레이아웃 변수 초기화 및 앱을 켜면 이전에 저장해둔 값 표시해주기
        personal_lev = (TextView) findViewById(R.id.level_present);
        personal_lev_img = (ImageView) findViewById(R.id.level_img);
        personal_nick = (TextView) findViewById(R.id.nickname);

        if(pref_str.equals("단계를 설정해주세요:)")) {
            personal_lev.setText(pref_str);
            personal_lev_img.setImageResource(R.drawable.level0);
        }
        else {

            switch (pref_str) {
                case "비건":
                    personal_lev.setText("당신은 " + pref_str + "이군요!");
                    personal_lev_img.setImageResource(R.drawable.level5);
                    break;
                case "락토":
                    personal_lev.setText("당신은 " + pref_str + "군요!");
                    personal_lev_img.setImageResource(R.drawable.level4);
                    break;
                case "락토오보":
                    personal_lev.setText("당신은 " + pref_str + "군요!");
                    personal_lev_img.setImageResource(R.drawable.level3);
                    break;
                case "세미":
                    personal_lev.setText("당신은 " + pref_str + "군요!");
                    personal_lev_img.setImageResource(R.drawable.level2);
                    break;
                case "페스코":
                    personal_lev.setText("당신은 " + pref_str + "군요!");
                    personal_lev_img.setImageResource(R.drawable.level1);
                    break;
                case "플렉시테리언":
                    personal_lev.setText("당신은 " + pref_str + "이군요!");
                    personal_lev_img.setImageResource(R.drawable.level0);
                    break;

            }
        }
        if(pref_str2.equals("비거닝님, 안녕하세요."))
            personal_nick.setText(pref_str2);
        else
            personal_nick.setText(pref_str2 + "님, 안녕하세요.");

        setLevel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SettingActivity.this, SettingLevel.class);
                startActivityForResult(intent,1000);
            }
        });

        setNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingNickname.class);
                startActivityForResult(intent,2000);
            }
        });

        //버튼 클릭 효과
        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView image = (TextView) v;
                float curX = event.getX();
                float curY = event.getY();

                switch (event.getAction()) {
                    //손가락 눌림
                    case MotionEvent.ACTION_DOWN: {
                        image.setBackgroundResource(R.drawable.rectangle_darkdashgap);
                        return false;
                    }
                    //손가락 뗌
                    case MotionEvent.ACTION_UP: {
                        image.setBackgroundResource(R.drawable.rectangle_dashgap);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        };
        setLevel.setOnTouchListener(touchListener);
        setNickname.setOnTouchListener(touchListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        personal_lev = (TextView) findViewById(R.id.level_present);
        personal_nick = (TextView) findViewById(R.id.nickname);
        if (requestCode == 1000)
        {

                if (resultCode == Activity.RESULT_OK) {
                    String sendText = data.getExtras().getString("결과");
                    switch (sendText) {
                        case "비건":
                            personal_lev.setText("당신은 " + sendText + "이군요!");
                            personal_lev_img.setImageResource(R.drawable.level5);
                            break;
                        case "락토":
                            personal_lev.setText("당신은 " + sendText + "군요!");
                            personal_lev_img.setImageResource(R.drawable.level4);
                            break;
                        case "락토오보":
                            personal_lev.setText("당신은 " + sendText + "군요!");
                            personal_lev_img.setImageResource(R.drawable.level3);
                            break;
                        case "세미":
                            personal_lev.setText("당신은 " + sendText + "군요!");
                            personal_lev_img.setImageResource(R.drawable.level2);
                            break;
                        case "페스코":
                            personal_lev.setText("당신은 " + sendText + "군요!");
                            personal_lev_img.setImageResource(R.drawable.level1);
                            break;
                        case "플렉시테리언":
                            personal_lev.setText("당신은 " + sendText + "이군요!");
                            personal_lev_img.setImageResource(R.drawable.level0);
                            break;
                    }
                    //Log.e("sendText", sendText);
                    pref_str = sendText;
                    editor.putString("Pref_str", pref_str);
                    editor.apply();
                }
                else if(resultCode == RESULT_CANCELED) {
                    return;
                }
                else{

                }
        }
        else if(requestCode == 2000){

            if (resultCode == Activity.RESULT_OK){
                String sendText2 = data.getExtras().getString("결과2");
                personal_nick.setText(sendText2 +"님, 안녕하세요.");
                pref_str2 = sendText2;
                editor.putString("Pref_nick",pref_str2);
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
