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

        adapter.addItem("채식주의가 뭔가요?", "The definition of vegan",
                "채식주의는 인간이 동물성 음식을 먹는 것을 피하고, 식물성 음식만을 먹는 것을 뜻합니다. " +
                        "동물성 음식은 보통 동물로 만든 음식과, 동물로부터 나온 유제품(우유, 버터 등), 동물의 알," +
                        " 동물 성분을 물에 넣고 끓인 국물과 어류까지도 포함하는 말이지만, " +
                        "일부 엄격하지 않은 채식의 경우에는 동물의 고기를 제외한 일부의 동물성 음식을 먹는 경우도 있습니다."
                ,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground);

        adapter.addItem("비건에도 단계가?", "6 stages of vegan","플렉시테리언은 전반적으로 채식 위주의 식사를 하지만 경우에 따라서는 육류나 생선을 먹는 단계이다.\n" +
                "세미는 육류를 제외하고 식사를 하는 단계이다.\n" +
                "페스코는 육류와 금류를 제외하고 식사를 하는 단계이다.\n" +
                "락토오보는 유제품과 꿀처럼 동물에게서 나오는 식품까지 허용하는 단계이다.\n" +
                "락토는 달걀을 제외한 유제품까지는 먹는 단계이다.\n" +
                "비건은 식물성 음식만을 먹는 단계이다.\n",R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground);

        adapter.addItem("비건을 하는 이유는?" , "Why do we eat vegetables","ewwewe",R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground);
        adapter.addItem("비건의 장점은?", "The advantage of vegan","jeeju",R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground);
        return root;
    }
}
