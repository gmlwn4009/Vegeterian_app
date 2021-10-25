package com.example.vegeproject.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.R;

public class SettingLevel extends AppCompatActivity {
    SharedPreferences pref2;
    SharedPreferences.Editor editor2;
    String savedClickedData;
    int level_nm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setlevel);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.RadioGroup1);//비건단계 라디오 그룹
        final RadioButton r_began = (RadioButton) findViewById(R.id.lev_began);
        final RadioButton r_lacto = (RadioButton) findViewById(R.id.lev_lacto);
        final RadioButton r_lactovo = (RadioButton) findViewById(R.id.lev_lactovo);
        final RadioButton r_pesco = (RadioButton) findViewById(R.id.lev_pesco);
        final RadioButton r_semi = (RadioButton) findViewById(R.id.lev_semi);
        final RadioButton r_flexi = (RadioButton) findViewById(R.id.lev_flexi);
        Button b = (Button) findViewById(R.id.select_ok);//설정 완료 버튼


        //Shared Preference 초기화
        pref2 = getSharedPreferences("pref2", Activity.MODE_PRIVATE);
        editor2 = pref2.edit();

        //저장값 불러오기
        savedClickedData = pref2.getString("SAVE_CHECK_DATA","");
        level_nm = pref2.getInt("SAVE_LEVEL_NUMBER",-1);
        //Log.e("savedClickedData",savedClickedData);

        //이전에 저장된값 보여주기
        switch (savedClickedData){
            case "비건":
                r_began.setChecked(true);
                break;
            case "락토":
                r_lacto.setChecked(true);
                break;
            case "락토오보":
                r_lactovo.setChecked(true);
                break;
            case "페스코":
                r_pesco.setChecked(true);
                break;
            case "세미":
                r_semi.setChecked(true);
                break;
            case "플렉시테리언":
                r_flexi.setChecked(true);
                break;
        }

        //설정 완료 버튼 클릭
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int id = rg.getCheckedRadioButtonId();
               RadioButton rb = (RadioButton) findViewById(id);
               level_nm=Math.abs(rg.indexOfChild(rb)-5);

               //아무것도 선택 안되었을때
               if(rb == null){
                    Toast.makeText(SettingLevel.this,"단계를 선택해주세요.",Toast.LENGTH_LONG).show();;
                }
               //단계가 선택되었을때
                else {

                   Intent data = new Intent();
                   data.putExtra("결과", rb.getText().toString());
                   savedClickedData = rb.getText().toString();
                   editor2.putString("SAVE_CHECK_DATA",savedClickedData);
                   editor2.putInt("SAVE_LEVEL_NUMBER",level_nm);
                   editor2.apply();

                   setResult(RESULT_OK, data);
                   finish();
                }
            }
        });





    }


}
