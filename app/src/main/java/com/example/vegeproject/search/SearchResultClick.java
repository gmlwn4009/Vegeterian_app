package com.example.vegeproject.search;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vegeproject.R;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultClick extends AppCompatActivity {

    private static final Pattern PATTERN_BRACKET = Pattern.compile("\\([^\\(\\)]+\\)");
    private static final String VOID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView productname = findViewById(R.id.productName);
        TextView productkind = findViewById(R.id.productKind);
        ImageView productimage = findViewById(R.id.productImage);
        TextView productLevel = findViewById(R.id.productLevel); // 알수없음 데이터 제거시 이 라인도 제거

        // 데이터 수신, 상품 세팅
        Intent intent = getIntent();
        productname.setText(intent.getStringExtra("prdlstNm"));
        productkind.setText("#"+intent.getStringExtra("prdKind"));
        Glide.with(this).load(intent.getStringExtra("imgUrl")).into(productimage);

        // 성분값 가공
        String allergy = intent.getStringExtra("allergy").replaceAll(" ","")
                .replaceAll("함유","")
                .replaceAll("유래원재료","")
                .replaceAll("\\*","")
                .replaceAll("\\·이제품은원재료에알레르기유발물질인", "") // 팔도부대찌개라면
                .replaceAll("을하고있습니다.", "") // 팔도부대찌개라면
                .replaceAll("식품", "") // 묵은지돼지김치찌개
                .replaceAll("※특정성분함량및원산지:후첨분말스프중닭고기0.62%", "") // 큰컵불닭볶음탕면
                .replaceAll("날류", "난류") // CJ해물볶음밥
                .replaceAll("조래규", "조개류") // 볶음진짬뽕
                .replaceAll("소고기", "쇠고기");;
        String[] allergies = deleteBracket(allergy).split(",");

        // 단계 분류
        boolean[] contain = classifyLevel(allergies);

        // 성분값 세팅
        setComponent(allergies, allergies.length);

        // 단계 세팅
        for (int i = 0; i < 5;i++)
            if (contain[i]) { setLevel(i); break; }
        if (contain[5])
            productLevel.setText("알 수 없음");
        else if ((contain[0] == false) && (contain[1] ==false)  && (contain[2] == false) && (contain[3] == false) && (contain[4] == false))
            setLevel(5);
}

    // 단계 분류
    public boolean[] classifyLevel(String[] allergies) {
        boolean [] b_list = new boolean[6];
        Arrays.fill(b_list,false);
        for (String s : allergies) {
            switch (s){
                //플렉시테리언
                case "돼지고기" :
                    b_list[0] = true;
                case "쇠고기" :
                    b_list[0] = true;
                    break;
                //세미
                case "닭고기" :
                    b_list[1] = true;
                    break;
                //페스코
                case "고등어" :
                    b_list[2] = true;
                case "새우" :
                    b_list[2] = true;
                case "게" :
                    b_list[2] = true;
                case "조개류" :
                    b_list[2] = true;
                case "오징어" :
                    b_list[2] = true;
                case "굴" :
                    b_list[2] = true;
                    break;
                //락토오보
                case "계란" :
                    b_list[3] = true;
                case "난류" :
                    b_list[3]= true;
                case "알류" :
                    b_list[3] = true;
                case "난황" :
                    b_list[3] = true;
                    break;
                //락토
                case "우유" :
                    b_list[4] = true;
                    break;
                //데이터 오류
                case "없음" :
                    b_list[5] = true;
                case "N/A" :
                    b_list[5] = true;
                    break;
            }
            /*
            if (s.contains("돼지고기")||s.contains("쇠고기"))
                b_list[0] = true;
            else if (s.contains("닭고기"))
                b_list[1] = true;
            else if (s.contains("고등어")||s.contains("새우")||s.contains("게")||s.contains("조개류")||s.contains("오징어")||s.contains("굴"))
                b_list[2] = true;
            else if (s.contains("난류")||s.contains("계란")||s.contains("알류")||s.contains("난황"))
                b_list[3] = true;
            else if (s.contains("우유"))
                b_list[4] = true;
            else if (s.contains("없음")||s.contains("N/A"))
                b_list[5] = true;

             */
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

        //단계 적합/부적합
        SharedPreferences prefLevel = getSharedPreferences("pref2", Activity.MODE_PRIVATE);//프리퍼런스 초기화
        SharedPreferences prefNick = getSharedPreferences("pref3",Activity.MODE_PRIVATE);
        String myNick = prefNick.getString("SAVE_CHANGED_DATA","비거닝");
        int myLevel = prefLevel.getInt("SAVE_LEVEL_NUMBER",-1);//변수에 나의 단계 저장
        TextView fitText = findViewById(R.id.fitness);

        if(myLevel < 0){
            fitText.setText("알레르기 성분이 없어요");
        }
        else {
            int len = myNick.length() + 6;

            fitText.setTextSize(15);
            fitText.setTextColor(Color.parseColor("#000000"));
            if (myLevel <= level) {
                Spannable span = new SpannableStringBuilder(myNick + "님이 섭취 가능한 제품이에요:)");
                span.setSpan(new ForegroundColorSpan(Color.BLUE), len, len+2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                fitText.setText(span);

            } else {
                Spannable span = new SpannableStringBuilder(myNick + "님은 섭취 불가능한 제품이에요;-;");
                span.setSpan(new ForegroundColorSpan(Color.RED), len, len+3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                fitText.setText(span);
            }
        }

    }

    // 괄호와 괄호 내부 내용 모두 삭제
    private static String deleteBracket(String text) {
        Matcher matcher = PATTERN_BRACKET.matcher(text);
        String pureText = text;
        String removeTextArea = new String();

        while(matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            removeTextArea = pureText.substring(startIndex, endIndex);
            pureText = pureText.replace(removeTextArea, VOID);
            matcher = PATTERN_BRACKET.matcher(pureText);
        }
        return pureText;
    }
}
