package com.example.vegeproject.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");
    private ArrayList<FirebaseData> arrayList = new ArrayList<FirebaseData>();
    public String searchItem;

    public ProgressDialog progressDialog;
    public static Context context;

    //최근 검색어 저장 array
    ArrayAdapter arrayAdapter;
    ArrayList<String> array_list;
    Set set = new HashSet();
    ListView listView;
    ArrayList<String> list = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;

        ImageView searchButton = findViewById(R.id.searchButton);
        listView = findViewById(R.id.listView);//최근검색어 저장용
        TextView deleteButton = findViewById(R.id.deleteText);//전체기록 삭제

        //shared preference불러오기 제발 불러와줘라 진짜 왜 안불러와지고 어이가없네
        ArrayList<String> list = getStringArrayPref(SearchActivity.this,"settings_item_json");
        if (array_list != null) {
            for (String value : list) {
                createTextview(value);
                Log.e("안되냐","아니");
            }
        }

        array_list = new ArrayList<String>();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 로딩창 객체 생성
                progressDialog = new ProgressDialog(context);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setCancelable(false);
                progressDialog.show();

                // 검색할 키워드 입력
                EditText editText = findViewById(R.id.editText);
                searchItem = editText.getText().toString();

                // 최근검색목록 update → 아직은 1 아이템만 가능, 2학기에 누적되도록 구현 예정
                createTextview(searchItem);

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


        //돋보기 아이콘 터치 효과
        searchButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) v;
                float curX = event.getX();
                float curY = event.getY();

                switch (event.getAction()) {
                    //손가락 눌림
                    case MotionEvent.ACTION_DOWN: {
                        image.setColorFilter(Color.parseColor("#608A2B"), PorterDuff.Mode.SRC_IN);
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

        //검색기록 전체 삭제
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                set.clear();
                arrayAdapter.clear();
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

    //최근검색어 생성
    private void createTextview(String searchItem){

        set.add(searchItem);
        if(array_list != null)
            array_list.clear();
        array_list.addAll(set);
        Collections.reverse(array_list);
        arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, array_list);
        listView.setAdapter(arrayAdapter);

        // ArrayList -> Json으로 변환
        setStringArrayPref(SearchActivity.this,"settings_item_json", array_list);

    }

    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        Log.e("안되냐2","아니");
        editor.apply();
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                    Log.e("안되냐3","아니");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return urls;
    }




}


