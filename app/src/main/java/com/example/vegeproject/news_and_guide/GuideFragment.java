package com.example.vegeproject.news_and_guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegeproject.R;

import java.util.ArrayList;

public class GuideFragment extends Fragment {
    private RecyclerView recyclerView1;
    private LinearLayoutManager layoutManager1;

    private Button button1;
    private ConstraintLayout expandableView1;
    private CardView cardView1;

    private ArrayList<news_item> itemArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guide, container, false);
        View v = inflater.inflate(R.layout.guide_card,container,false);

        button1 = v.findViewById(R.id.button1);
        expandableView1 = v.findViewById(R.id.expandable_view1);
        cardView1 = v.findViewById(R.id.card1);
        itemArrayList = new ArrayList<>();

        recyclerView1 = root.findViewById(R.id.recycler_view);
        layoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView1.setLayoutManager(layoutManager1);
        GuideAdapter adapter = new GuideAdapter();
        recyclerView1.setAdapter(adapter);

        adapter.addItem("비건이 뭔가요?", "The definition of vegan", "비건은 beginnig과 vegeterain의 합성어 입니다.",R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground);
//        adapter.addItem("비건에도 단계가?", "6 stages of vegan","hello",R.drawable.ic_launcher_foreground,R.drawable.ic_baseline_eco_24);
//        adapter.addItem("비건의 장점은?", "The advantage of vegan","jeeju",R.drawable.ic_launcher_foreground,R.drawable.ic_baseline_eco_24);
//        adapter.addItem("title", "This is a content space","ewwewe",R.drawable.ic_launcher_foreground,R.drawable.ic_baseline_eco_24);
//        adapter.addItem("title", "This is a content space","we",R.drawable.ic_launcher_foreground,R.drawable.ic_baseline_eco_24);

        return root;
    }
}
