package com.example.vegeproject.news_and_guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vegeproject.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<news_item> items = new ArrayList<news_item>();

    public void addItem(String title, String name, String email){
        news_item item = new news_item();

        item.setTitle(title);
        item.setName(name);
        item.setEmail(email);

        items.add(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView mTitle;
        public TextView mname;
        public TextView mEmail;
        public ImageView mPhoto;

        public ViewHolder(View v) {
            super(v);

            mTitle = (TextView) v.findViewById(R.id.title_view);
            mname = (TextView) v.findViewById(R.id.subtitle_view);
            mEmail = (TextView) v.findViewById(R.id.email_view);
        }

        public void setItem(news_item item){
            mTitle.setText(item.getTitle());
            mname.setText(item.getName());
            mEmail.setText(item.getEmail());
        }
    }

    //뷰홀더
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.news_card,viewGroup,false);
        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        news_item item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}