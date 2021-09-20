package com.example.vegeproject.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vegeproject.R;

import java.util.Arrays;

public class SearchResultClick extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView productname = findViewById(R.id.productname);
        ImageView productimage = findViewById(R.id.productimage);
        TextView productlevel = findViewById(R.id.productlevel);
        ImageView productlevelimage = findViewById(R.id.productlevelimage);

        // 데이터 수신, 상품 세팅
        Intent intent = getIntent();
        productname.setText(intent.getStringExtra("prdlstNm"));
        Glide.with(this).load(intent.getStringExtra("imgUrl")).into(productimage);

        // 알러지값 가공
        String allergy = intent.getStringExtra("allergy").replaceAll(" ","").replaceAll("함유","").replaceAll("유래원재료","").replaceAll("\\*","").replaceAll("소고기", "쇠고기");;
        String[] allergies = allergy.split(",");

        // 알러지값 세팅
        setTextView (allergies, allergies.length);

        // 단계 분류
        boolean[] contain = classifyLevel(allergies);

        // 단계 세팅
        for (int i = 0; i < 5;i++) {
            if (contain[i]) {
                setLevel(i);
                break;
            }
        }
        if (contain[5]) {
            productlevel.setText("알 수 없음");
        }
        else if ((contain[0] == false) && (contain[1] ==false)  && (contain[2] == false) && (contain[3] == false) && (contain[4] == false)) {
            setLevel(5);
        }

    }

    // 알러지값 세팅
    private void setTextView(String[] allergies, int length) {
        int i, resID;
        for (i = 0; i < length; i++) {
            resID = getResources().getIdentifier("component"+(i+1), "id", this.getPackageName());
            TextView component = findViewById(resID);
            component.setText(allergies[i]);
        }
    }

    // 단계 분류
    private boolean[] classifyLevel(String[] allergies) {
        boolean [] b_list = new boolean[6];
        Arrays.fill(b_list,false);
        for (String s : allergies) {
            if (s.contains("돼지고기")||s.contains("쇠고기"))
                b_list[0] = true;
            else if (s.contains("닭고기"))
                b_list[1] = true;
            else if (s.contains("고등어")||s.contains("새우")||s.contains("게")||s.contains("조개류")||s.contains("오징어"))
                b_list[2] = true;
            else if (s.contains("난류")||s.contains("계란")||s.contains("알류"))
                b_list[3] = true;
            else if (s.contains("우유"))
                b_list[4] = true;
            else if (s.contains("없음")||s.contains("N/A"))
                b_list[5] = true;
        }
        return b_list;
    }

    // 단계 세팅
    private void setLevel(int level) {
        // 단계 이름
        TextView productlevel = findViewById(R.id.productlevel);
        int txtID = getResources().getIdentifier("text"+level, "string", this.getPackageName());
        productlevel.setText(Html.fromHtml("<font color=\"#81b41a\"><b>"+getString(txtID)+"</b></font>"+"<font color=\"#000000\">"+" 단계까지 먹을 수 있어요!"+"</font>"));

        // 단계 이미지
        ImageView productlevelimage = findViewById(R.id.productlevelimage);
        int imgID = getResources().getIdentifier("level"+level, "drawable", this.getPackageName());
        productlevelimage.setImageResource(imgID);

        // 단계 버튼
        for (int i = level; i >= 0; i--) {
            int btnID = getResources().getIdentifier("btn"+i, "id", this.getPackageName());
            TextView btn = findViewById(btnID);
            int colorID = getResources().getIdentifier("rectangle_level", "drawable", this.getPackageName());
            btn.setBackgroundResource(colorID);
        }
    }

}
