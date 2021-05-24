package com.example.vegeproject.news_and_guide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegeproject.R;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    static private ArrayList<news_item> items = new ArrayList<news_item>();
    private LayoutInflater mInflate;
    private Context mContext;

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListner =null;

    //어댑터 내 커스텀 리스너 인터페이스정의
    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    //OnItemClickListener 객체 참조르 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListner =listener;
    }

    public NewsAdapter(Context context, ArrayList<news_item> itm){
        this.items = itm;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void addItem(String title, String company, String pubDate, String link){
        news_item item = new news_item();

        item.setTitle(title);
        item.setCompany(company);
        item.setLink(link);
        item.setPubDate(pubDate);

        items.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView mTitle;
        public TextView mcompany;
        public TextView mpubDate;


        ViewHolder(View v) {
            super(v);

            mTitle = (TextView) v.findViewById(R.id.title_view);
            mcompany=(TextView) v.findViewById(R.id.company_view);
            mpubDate = (TextView) v.findViewById(R.id.pubDate_view);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        //리스너 객체의 메서드 호출,,,
                        if(mListner != null){
                            mListner.onItemClick(v, pos);

                        }
                    }
                }
            });

        }

        public void setItem(news_item item){
            mTitle.setText(item.getTitle());
            mcompany.setText(item.getCompany());
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
        holder.mTitle.setText(items.get(position).title);
        holder.mcompany.setText(items.get(position).company);
        holder.mpubDate.setText(items.get(position).pubDate);

        //클릭하면 뉴스 링크 타고 들어가기
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(items.get(position).link));
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}