package com.example.vegeproject.news_and_guide;

import android.content.Context;
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
    private LayoutInflater mInflate;
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<news_item> itm){
        this.items = itm;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void addItem(String title, String company, String pubDate){
        news_item item = new news_item();

        item.setTitle(title);
        item.setCompany(company);
        //item.setLink(link);
        item.setPubDate(pubDate);

        items.add(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView mTitle;
        public TextView mcompany;
        public TextView mlink;
        public TextView mpubDate;
        public ImageView mPhoto;

        public ViewHolder(View v) {
            super(v);

            mTitle = (TextView) v.findViewById(R.id.title_view);
            mcompany=(TextView) v.findViewById(R.id.company_view);
            //mlink = (TextView) v.findViewById(R.id.link_view);
            mpubDate = (TextView) v.findViewById(R.id.pubDate_view);
        }

        public void setItem(news_item item){
            mTitle.setText(item.getTitle());
            mcompany.setText(item.getCompany());
            //mlink.setText(item.getLink());
            mpubDate.setText(item.getPubDate());
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