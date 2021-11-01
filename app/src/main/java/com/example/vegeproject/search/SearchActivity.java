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
import android.widget.AdapterView;
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

    //Shared Preference 생성
    SharedPreferences pref;//프리퍼런스
    SharedPreferences.Editor editor;//에디터

    //getStringArrayPref() 결과값을 담아낼 ArrayList<String>
    private ArrayList<String> getStringArrayPrefList;

    //Json array
    JSONArray a = new JSONArray();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;

        ImageView searchButton = findViewById(R.id.searchButton);
        listView = findViewById(R.id.listView);//최근검색어 저장용
        TextView deleteButton = findViewById(R.id.deleteText);//전체기록 삭제
        TextView deleteselecButton = findViewById(R.id.deleteselecText);//선택기록 삭제

        //프리퍼런스 초기화
        // Shared Preference 초기화
        pref = getSharedPreferences("prefrecent", Activity.MODE_PRIVATE);
        editor = pref.edit();


        array_list = new ArrayList<String>();

        getpref();

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

                // 최근검색목록 update
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

        //최근 검색기록 클릭시 검색되는...
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) parent.getItemAtPosition(position);
                EditText editText = findViewById(R.id.editText);
                editText.setText(data);

                // 로딩창 객체 생성
                progressDialog = new ProgressDialog(context);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setCancelable(false);
                progressDialog.show();

                searchItem = data;
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
        searchButton.setOnTouchListener(new View.OnTouchListener() {
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

        //검색 기록 선택 삭제
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = arrayAdapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listView.getCheckedItemPosition();

                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        array_list.remove(checked) ;

                        // listview 선택 초기화.
                        listView.clearChoices();

                        // listview 갱신.
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;


        //검색기록 전체 삭제
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!set.isEmpty()){
                    set.clear();
                    arrayAdapter.clear();
                    clearShared();//Shared도 함께 삭제제
                }
             else{//기록 없을때 암껏도 실행 안하기

               }
            }

        });


    }

    public void readFirebaseData(MyCallback myCallback) {

        arrayList.clear(); // 초기화

        databaseReference.orderByChild("prdlstNm").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    // 검색시 띄어쓰기 제한을 없애기 위해 사용자 입력 String과 파이어베이스 제품명 String에서 띄어쓰기 제거
                    String temp = searchItem.replaceAll(" ", "");
                    String tempfb = data.child("prdlstNm").getValue(String.class).replaceAll(" ", "");

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
    private void createTextview(String searchItem) {


        set.add(searchItem);
        array_list.clear();
        array_list.addAll(set);
        Collections.reverse(array_list);
        arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_multiple_choice, array_list);
        listView.setAdapter(arrayAdapter);

        //sharedpreference
        ArrayList<String> list = new ArrayList<String>();
        list.add(searchItem);
        setStringArrayPref(SearchActivity.this, "RECENTSEARCH", list);

    }

    //Shared 비우는 메소드
    public void clearShared() {
        editor.clear();
        editor.commit();
    }

   //최근 shared preference 가져오기
    public void getpref(){

        // 저장해둔 값 불러오기
        getStringArrayPrefList = getStringArrayPref(SearchActivity.this, "RECENTSEARCH");
        if (getStringArrayPrefList != null) {
            for (String value : getStringArrayPrefList) {
                set.add(value);
            }
            array_list.clear();
            array_list.addAll(set);
            Collections.reverse(array_list);
            arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_multiple_choice, array_list);
            listView.setAdapter(arrayAdapter);

        }
    }

    // 매개변수로 shared에 저장할 key값과 ArrayList<String> 타입의 List를 넣어주면 shared에 저장하는 메소드
    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {

        for (int i = getStringArrayPrefList.size()-1; i >=0; i--) {
            a.put(getStringArrayPrefList.get(i));
        }
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }


    //매개변수로 저장한 Key값을 입력해주고 받을 ArrayList<String>에 담아내는 메소드
    private ArrayList<String> getStringArrayPref(Context context, String key) {
        String json = pref.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }



}


