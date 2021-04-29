package com.example.vegeproject.news_and_guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public void addItem(String title, String subtitle, String content, int image1, int image2){
        guide_item item = new guide_item();

        item.setTitle(title);
        item.setContent(content);
        item.setSubtitle(subtitle);
        item.setPhoto1(image1);
        item.setPhoto2(image2);
        items.add(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView mTitle, subTitle;
        public TextView mContent;
        public ImageView imageView1, imageView2;
        public LinearLayout btnSearch;

        public ViewHolder(View v) {
            super(v);

            mTitle = (TextView) v.findViewById(R.id.title_view);
            subTitle = (TextView) v.findViewById(R.id.subtitle_view);
            mContent = (TextView) v.findViewById(R.id.content_view);
            imageView1 = (ImageView) v.findViewById(R.id.imageView1);
            imageView2 = (ImageView) v.findViewById(R.id.image_view2);
            btnSearch = v.findViewById(R.id.btnSearch);
        }

        public void setItem(guide_item item){
            mTitle.setText(item.getTitle());
            subTitle.setText(item.getSubtitle());
            mContent.setText(item.getContent());
            imageView1.setImageResource(item.getPhoto1());
            imageView2.setImageResource(item.getPhoto2());
        }
    }

    //뷰홀더
    // Create new views (invoked by the layout manager)
    @Override
    public explanation onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.guide_card,viewGroup,false);
        return new explanation(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(explanation holder, int position) {
        guide_item item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.content.setText(item.getContent());
        holder.photo1.setImageResource(item.getPhoto1());
        holder.photo2.setImageResource(item.getPhoto2());

        boolean isExpandable = items.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class explanation extends RecyclerView.ViewHolder {

        TextView title, subtitle, content;
        ImageView photo1, photo2;
        LinearLayout linearLayout;
        ConstraintLayout expandableLayout;

        public explanation(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_view);
            subtitle = itemView.findViewById(R.id.subtitle_view);
            content = itemView.findViewById(R.id.content_view);
            photo1 = itemView.findViewById(R.id.imageView1);
            photo2 = itemView.findViewById(R.id.image_view2);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_view1);

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