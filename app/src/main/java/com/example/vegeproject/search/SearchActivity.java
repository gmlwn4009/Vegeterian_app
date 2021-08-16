package com.example.vegeproject.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vegeproject.MainActivity;
import com.example.vegeproject.R;
import com.example.vegeproject.barcode.result_fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class SearchActivity extends FragmentActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");
    private FirebaseList firebaseList = new FirebaseList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //search fragment 생성
        search_fragment sf = new search_fragment();

        //fragment manager 선언 및 삽입
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.add(R.id.fragment_container_search, sf);
        fragmentManager.commit();

        ImageView searchButton = findViewById(R.id.searchButton) ;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView recentSearchItem = findViewById(R.id.recentSearchItem);

                // 검색할 키워드 입력
                EditText editText = findViewById(R.id.editText);

                // 입력받은 키워드를 최근검색목록에 반영 → 아직은 1 아이템만 가능, 2학기에 누적되도록 구현 예정
                recentSearchItem.setText(editText.getText());

                // 입력값을 데이터베이스에서 검색하기 위한 함수 호출
                readFirebaseList(editText.getText().toString());

                //result fragment 생성 및 전환
                result_fragment rf = new result_fragment();
                FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
                fragmentManager.add(R.id.fragment_container_search, rf);
                fragmentManager.commit();
            }
        }) ;

    }

    private void readFirebaseList(String searchItem){

        // 입력값과 데이터베이스의 prdlstNm값이 일치하는 경우
        databaseReference.orderByChild("prdlstNm").equalTo(searchItem).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    // firebaseList 클래스에 prdlstNm, allergy, barcode 값을 저장
                    firebaseList.prdlstNm = data.child("prdlstNm").getValue(String.class);
                    firebaseList.allergy = data.child("allergy").getValue(String.class);
                    firebaseList.barcode = data.child("barcode").getValue(String.class);

                    // 저장한 값을 로그로 확인
                    Log.w("FirebaseData", firebaseList.prdlstNm + ", " + firebaseList.allergy + ", " + firebaseList.barcode);

                    // 알러지값 가공
                    firebaseList.allergy = firebaseList.allergy.replaceAll(" ","").replaceAll("함유","").replaceAll("유래원재료","").replaceAll("\\*","").replaceAll("소고기", "쇠고기");;
                    String[] allergies = firebaseList.allergy.split(",");
                    for (int i=0; i<allergies.length; i++)
                        Log.w("ArrayData", allergies[i]);

                    //단계별 구분
                    boolean [] b_list = new boolean[6];
                    Arrays.fill(b_list,false);

                    String[] began = {"플렉시테리언","세미","페스코","락토오보","락토","비건"};

                    for(String s : allergies) {
                        if(s.contains("돼지고기")||s.contains("소고기"))
                            b_list[0] = true;
                        else if(s.contains("닭고기"))
                            b_list[1] = true;
                        else if(s.contains("고등어")||s.contains("새우")||s.contains("게")||s.contains("조개류")||s.contains("오징어"))
                            b_list[2] = true;
                        else if(s.contains("난류")||s.contains("계란"))
                            b_list[3] = true;
                        else if(s.contains("우유"))
                            b_list[4] = true;
                        else if(s.contains("없음")||s.contains("N/A"))
                            b_list[5] = true;
                    }
                    /**
                    for (int i=0; i<b_list.length; i++) {
                        if(b_list[i])
                        System.out.println("성분"+i);
                    }
                     **/
                   for(int i =0 ; i<5;i++){
                       if(b_list[i]){
                           System.out.println(began[i]);
                           break;
                       }
                   }
                   if (b_list[5])
                        System.out.println("알수없음");
                   else if ((b_list[0] == false) && (b_list[1] ==false)  && (b_list[2] == false) && (b_list[3] == false) && (b_list[4] == false))
                        System.out.println("비건");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
