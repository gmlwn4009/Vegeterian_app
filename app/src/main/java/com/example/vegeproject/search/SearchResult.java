package com.example.vegeproject.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vegeproject.R;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<RecyclerViewItem> rvList;
    private ArrayList<FirebaseData> fbList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // 데이터 수신
        Intent intent = getIntent();
        fbList = (ArrayList<FirebaseData>)intent.getSerializableExtra("list");
        for (int i=0; i<fbList.size(); i++)
            System.out.println(i + ": " + fbList.get(i).prdlstNm+ " / " + fbList.get(i).allergy + " / " + fbList.get(i).barcode);

        // 어댑터 초기화
        searchAdapter = new SearchAdapter();

        // 리사이클러뷰 초기화
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터에 데이터 적용
        rvList = new ArrayList<>();
        for(int i=0; i<fbList.size(); i++){
            rvList.add(new RecyclerViewItem(fbList.get(i).prdlstNm, fbList.get(i).imgUrl));
        }
        searchAdapter.setArrayList(rvList);

    }

}