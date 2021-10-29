package com.example.vegeproject.news_and_guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegeproject.R;

import java.util.ArrayList;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.explanation> {

    private ArrayList<guide_item> items = new ArrayList<guide_item>();

    public void addItem(String title, String subtitle, String content, int image){
        guide_item item = new guide_item();

        item.setTitle(title);
        item.setContent(content);
        item.setSubtitle(subtitle);
        item.setPhoto(image);
        items.add(item);
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView mTitle, subTitle;
        public TextView mContent;
        public ImageView imageView;
        public LinearLayout btnSearch;

        // 뷰 객체 참조
        public ViewHolder(View v) {
            super(v);

            mTitle = (TextView) v.findViewById(R.id.guide_title);
            subTitle = (TextView) v.findViewById(R.id.guide_subtitle);
            mContent = (TextView) v.findViewById(R.id.guide_content);
            imageView = (ImageView) v.findViewById(R.id.guide_image);
            btnSearch = v.findViewById(R.id.btnSearch);
        }

        public void setItem(guide_item item){
            mTitle.setText(item.getTitle());
            subTitle.setText(item.getSubtitle());
            mContent.setText(item.getContent());
            imageView.setImageResource(item.getPhoto());
        }
    }

    // 뷰홀더
    // Create new views (invoked by the layout manager)
    // viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성
    @Override
    public explanation onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.guide_card,viewGroup,false);
        return new explanation(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(explanation holder, int position) {
        guide_item item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.content.setText(item.getContent());
        holder.photo.setImageResource(item.getPhoto());

        boolean isExpandable = items.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    // 전체 아이템 개수 리턴
    @Override
    public int getItemCount() {
        return items.size();
    }

    // 리사이클러뷰의 확장 (클릭 시 아래로 확장되면서 숨겨진 레이아웃이 나온다)
    public class explanation extends RecyclerView.ViewHolder {

        TextView title, subtitle, content;
        ImageView photo;
        LinearLayout linearLayout;
        ConstraintLayout expandableLayout;

        public explanation(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.guide_title);
            subtitle = itemView.findViewById(R.id.guide_subtitle);
            content = itemView.findViewById(R.id.guide_content);
            photo = itemView.findViewById(R.id.guide_image);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_view);

            // 클릭 시 호출되는 메소드
            linearLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    guide_item guideItem = items.get(getAdapterPosition());
                    guideItem.setExpandable(!guideItem.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}