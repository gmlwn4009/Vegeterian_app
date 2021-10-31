package com.example.vegeproject.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vegeproject.R;

public class SettingNickname extends Activity {
    private EditText editNick;
    SharedPreferences pref3;
    SharedPreferences.Editor editor3;
    String savedChangedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setnickname);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editNick = (EditText) findViewById(R.id.put_nickname);
        Button b = (Button) findViewById(R.id.change_ok); // 설정 완료 버튼

        // Shared Preference 초기화
        pref3 = getSharedPreferences("pref3", Activity.MODE_PRIVATE);
        editor3 = pref3.edit();

        // 저장값 불러오기
        savedChangedData = pref3.getString("SAVE_CHANGED_DATA","");

        // 이전에 저장된값 보여주기
        if (savedChangedData.equals(""))
            editNick.setHint("닉네임");
        else {
            editNick.setHint(savedChangedData);
        }

        // 설정 완료 버튼 클릭
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 아무것도 선택 안 되었을 때
                if (editNick == null) {
                    Toast.makeText(SettingNickname.this,"닉네임을 입력해주세요.",Toast.LENGTH_LONG).show();;
                }
                // 단계가 선택되었을 때
                else {

                    Intent data = new Intent();
                    data.putExtra("결과2", editNick.getText().toString());
                    savedChangedData = editNick.getText().toString();
                    editor3.putString("SAVE_CHANGED_DATA",savedChangedData);
                    editor3.apply();

                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}