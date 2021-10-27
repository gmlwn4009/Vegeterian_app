package com.example.vegeproject.news_and_guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    private LinearLayout btnSearch;
    private ConstraintLayout expandableView1;
    private CardView cardView1;

    private ArrayList<news_item> itemArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guide, container, false);
        View v = inflater.inflate(R.layout.guide_card,container,false);

        btnSearch = v.findViewById(R.id.btnSearch);
        expandableView1 = v.findViewById(R.id.expandable_view1);
        cardView1 = v.findViewById(R.id.card1);
        itemArrayList = new ArrayList<>();

        recyclerView1 = root.findViewById(R.id.recycler_view);
        layoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView1.setLayoutManager(layoutManager1);
        GuideAdapter adapter = new GuideAdapter();
        recyclerView1.setAdapter(adapter);

        adapter.addItem("채식주의가 뭔가요?", "The definition of vegan",
                " 1944년, 유제품을 먹지 않는 채식주의자에 대한 개념을 탐구하던 중 beginning과 vegetarian을 결합해 vegan이라는 용어를 제시했습니다." +
                        "채식주의는 인간이 동물성 음식을 먹는 것을 피하고, 식물성 음식만을 먹는 것을 뜻합니다. " +
                        "동물성 음식은 보통 동물로 만든 음식과, 동물로부터 나온 유제품(우유, 버터 등), 동물의 알, " +
                        "동물 성분을 물에 넣고 끓인 국물과 어류까지도 포함하는 말이지만 " +
                        "일부 엄격하지 않은 채식의 경우에는 동물의 고기를 제외한 일부의 동물성 음식을 먹는 경우도 있습니다."
                ,R.drawable.icon_carrot,R.drawable.vegan_definition);

        adapter.addItem("비건에도 단계가?", "6 stages of vegan"," 비건은 총 6단계로 이루어져 있습니다." +
                "상황에 따라 육류를 허용하는 플렉시테리언부터 육류를 아예 섭취하지 않는 비건까지 채식의 종류는 다양합니다." +
                "각각의 비건 단계는 육류, 가금류, 해산물, 달걀, 유제품, 채소, 이렇게 6가지의 카테고리로 나누어져 있습니다. " +
                "채식을 시작할 때는 본인의 체질과 건강을 고려하여 단계를 골라 실천하는 것이 좋습니다.",R.drawable.icon_apple,R.drawable.vegan_stage);

        adapter.addItem("비건을 하는 이유는?" , "Why do we eat vegetables",
                " 크게 환경적인 이유와 윤리적인 이유로 나눌 수 있습니다."+
                        "인간들의 육식을 위해서 식용 동물들을 대규모로 사육하고 있습니다. " +
                        "유엔 식량농업기구의 보고에 따르면 가축에게서 나오는 이산화탄소가 교통수단보다 약 18배가 많이 방출된다고 합니다. " +
                        "사료에서 만들어지는 이산화질소와 동물들이 배출하는 메탄가스는 지구온난화의 가장 큰 원인이 된다고 합니다. " +
                        "윤리적인 측면에서는 가축의 사육환경과 도축과정 등을 문제 삼습니다." +
                        "인간들이 먹고 있는 고기들은 동물들이 자연적으로 죽어서 생긴 것들이 아닙니다. " +
                        "채식주의자들은 '살아있는 생명을 죽일 권리는 없다'는 동물권을 주장하면서 윤리적인 이유로 육식을 거부합니다.",R.drawable.icon_radish,R.drawable.icon_eco);

        adapter.addItem("비건의 장점은?", "The advantage of vegan",
                " 채식주의자들은 심장병이나 당뇨, 고혈압 등의 각종 성인병에 걸릴 확률이 낮습니다. " +
                "채식을 하게 되면 포화지방과 콜레스테롤의 섭취가 줄어 적정 체중을 유지할 수 있고 비만을 막을 수 있습니다. " +
                "아토피와 같은 피부질환도 줄일 수 있다고 합니다. 채식주의자들은 육식을 하는 사람들에 비해 수명이 길다는 연구 결과도 있습니다." ,R.drawable.icon_tomato,R.drawable.icon_nature);
        return root;
    }
}
