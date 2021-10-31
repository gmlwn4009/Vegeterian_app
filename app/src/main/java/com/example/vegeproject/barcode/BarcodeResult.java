package com.example.vegeproject.barcode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vegeproject.R;
import com.example.vegeproject.search.FirebaseData;

import java.util.Arrays;

public class BarcodeResult extends AppCompatActivity {

    private FirebaseData firebaseData;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView productName = findViewById(R.id.productName);
        TextView productkind = findViewById(R.id.productKind);
        ImageView productImage = findViewById(R.id.productImage);

        // 데이터 수신
        Intent intent = getIntent();
        firebaseData = (FirebaseData)intent.getSerializableExtra("data");
        System.out.println(firebaseData.prdlstNm+ " / " + firebaseData.allergy + " / " + firebaseData.barcode);

        productName.setText(firebaseData.prdlstNm);
        productkind.setText("#"+firebaseData.prdKind);
        Glide.with(this).load(firebaseData.getImgUrl()).into(productImage);

        // 성분값 가공
        String[] allergies = firebaseData.allergy.split(",");

        // 단계 분류
        boolean[] contain = classifyLevel(allergies);

        // 성분값 세팅
        setComponent(allergies, allergies.length);

        // 단계 세팅
        for (int i = 0; i < 6;i++)
            if (contain[i]) { setLevel(i); break; }

    }

    // 단계 분류
    public boolean[] classifyLevel(String[] allergies) {
        boolean [] b_list = new boolean[6];
        Arrays.fill(b_list,false);
        for (String s : allergies) {
            switch (s){
                //플렉시테리언
                case "돼지고기" : case "쇠고기" :
                    b_list[0] = true;
                    break;
                //세미
                case "닭고기" :
                    b_list[1] = true;
                    break;
                //페스코
                case "고등어" : case "새우" : case "게" : case "조개류" : case "오징어" : case "굴" :
                    b_list[2] = true;
                    break;
                //락토오보
                case "계란" : case "난류" : case "알류" : case "난황" :
                    b_list[3] = true;
                    break;
                //락토
                case "우유" :
                    b_list[4] = true;
                    break;
                //비건
                default:
                    b_list[5] = true;
                    break;
            }
        }
        return b_list;
    }

    // 성분값 세팅
    public void setComponent(String[] allergies, int length) {
        int i, componentID, underlineID;
        for (i = 0; i < 10; i++) {
            componentID = getResources().getIdentifier("component" + (i + 1), "id", this.getPackageName());
            TextView component = findViewById(componentID);

            // 텍스트뷰에 성분값 적용
            if (i < length)
                component.setText(allergies[i]);

                // 성분값 없는 레이아웃 제거
            else {
                component.setVisibility(View.GONE);
                if (i > 5) {
                    underlineID = getResources().getIdentifier("underline" + (i + 1), "id", this.getPackageName());
                    View underline = findViewById(underlineID);
                    underline.setVisibility(View.GONE);
                }
            }
        }
    }

    // 단계 세팅
    public void setLevel(int level) {
        // 단계 이름
        TextView productlevel = findViewById(R.id.productLevel);
        int txtID = getResources().getIdentifier("text"+level, "string", this.getPackageName());
        productlevel.setText(Html.fromHtml("<font color=\"#81b41a\"><b>"+getString(txtID)+"</b></font>"+"<font color=\"#000000\">"+" 단계까지 먹을 수 있어요!"+"</font>"));

        // 단계 이미지
        ImageView productlevelimage = findViewById(R.id.productLevelImage);
        int imgID = getResources().getIdentifier("level"+level, "drawable", this.getPackageName());
        productlevelimage.setImageResource(imgID);

        // 단계 버튼
        for (int i = level; i >= 0; i--) {
            int btnID = getResources().getIdentifier("btn"+i, "id", this.getPackageName());
            TextView btn = findViewById(btnID);
            int colorID = getResources().getIdentifier("rectangle_level", "drawable", this.getPackageName());
            btn.setBackgroundResource(colorID);
        }

        // 단계 적합 여부
        SharedPreferences prefLevel = getSharedPreferences("pref2", Activity.MODE_PRIVATE); // 프리퍼런스 초기화
        SharedPreferences prefNick = getSharedPreferences("pref3",Activity.MODE_PRIVATE);
        String myNick = prefNick.getString("SAVE_CHANGED_DATA","비거닝");
        int myLevel = prefLevel.getInt("SAVE_LEVEL_NUMBER",-1); // 변수에 나의 단계 저장
        TextView fitText = findViewById(R.id.fitness);

        if (myLevel >= 0) {
            int myLevelID = getResources().getIdentifier("text"+myLevel, "string", this.getPackageName());
            fitText.setTextSize(14);
            fitText.setTextColor(Color.parseColor("#000000"));

            if (myLevel <= level)
                fitText.setText(Html.fromHtml(getString(myLevelID) + "인 " + myNick + "님은<br>섭취 " + "<font color=\"#81b41a\"><b>" + "가능" + "</b></font>" + "한 제품이에요."));
            else
                fitText.setText(Html.fromHtml(getString(myLevelID) + "인 " + myNick + "님은<br>섭취 " + "<font color=\"#f44336\"><b>" + "불가능" + "</b></font>" + "한 제품이에요."));
        }
    }
}
