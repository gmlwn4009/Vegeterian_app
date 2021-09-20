package com.example.vegeproject.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegeproject.R;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<FirebaseData> fbList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // 데이터 수신
        Intent intent = getIntent();
        fbList = (ArrayList<FirebaseData>)intent.getSerializableExtra("list");
        for (int i=0; i<fbList.size(); i++) // 수신 확인
            System.out.println(i + ": " + fbList.get(i).prdlstNm+ " / " + fbList.get(i).allergy + " / " + fbList.get(i).barcode);

        // 어댑터 초기화
        searchAdapter = new SearchAdapter();

        // 리사이클러뷰 초기화
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터에 데이터 적용
        searchAdapter.setArrayList(fbList);

        // 아이템 클릭시 화면 전환
        searchAdapter = new SearchAdapter(this, fbList);

    }

}