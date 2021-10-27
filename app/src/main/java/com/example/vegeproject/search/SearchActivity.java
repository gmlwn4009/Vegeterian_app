package com.example.vegeproject.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");
    private ArrayList<FirebaseData> arrayList = new ArrayList<FirebaseData>();
    public String searchItem;

    public ProgressDialog progressDialog;
    public static Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;

        ImageView searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 로딩창 객체 생성
                progressDialog = new ProgressDialog(context);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setCancelable(false);
                progressDialog.show();

                TextView recentSearchItem = findViewById(R.id.recentSearchItem);

                // 검색할 키워드 입력
                EditText editText = findViewById(R.id.editText);
                searchItem = editText.getText().toString();

                // 최근검색목록 update → 아직은 1 아이템만 가능, 2학기에 누적되도록 구현 예정
                recentSearchItem.setText(editText.getText());

                readFirebaseData(new MyCallback() {
                    @Override
                    public void onCallback(ArrayList<FirebaseData> list) {
                        arrayList = list;
                        Intent intent = new Intent(getApplicationContext(), SearchResult.class);
                        intent.putExtra("list", arrayList); // 데이터 송신
                        startActivity(intent);
                    }
                });
            }
        });
        searchButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                float curX = event.getX();
                float curY = event.getY();

                switch (event.getAction()) {
                    //손가락 눌림
                    case MotionEvent.ACTION_DOWN: {
                        image.setColorFilter(Color.parseColor("#4D000000"), PorterDuff.Mode.DST_OVER);
                        return false;
                    }
                    //손가락 뗌
                    case MotionEvent.ACTION_UP: {
                        image.setColorFilter(null);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        });
    }

    public void readFirebaseData(MyCallback myCallback){

        arrayList.clear(); // 초기화

        databaseReference.orderByChild("prdlstNm").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){

                    // 검색시 띄어쓰기 제한을 없애기 위해 사용자 입력 String과 파이어베이스 제품명 String에서 띄어쓰기 제거
                    String temp = searchItem.replaceAll(" ","");
                    String tempfb = data.child("prdlstNm").getValue(String.class).replaceAll(" ","");

                    if (tempfb.contains(temp))
                        arrayList.add(new FirebaseData(data.child("prdlstNm").getValue(String.class),
                                data.child("barcode").getValue(String.class),
                                data.child("allergy").getValue(String.class),
                                data.child("imgurl").getValue(String.class),
                                data.child("prdkind").getValue(String.class)));
                }

                myCallback.onCallback(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public interface MyCallback {
        void onCallback(ArrayList<FirebaseData> list);
    }
}