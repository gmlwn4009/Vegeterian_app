package com.example.vegeproject.news_and_guide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegeproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private ArrayList<news_item> itemArrayList=null;
    news_item news=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        View v = inflater.inflate(R.layout.news_card, container, false);

        itemArrayList = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);


        class news_getXML extends AsyncTask<String, Void, String> {
            //asynctask<doinbackground 파라미터타입&excute메소드 인자값,doinbackground진행단위의 타입&onprogressUpdate파라미터타입,doInBackground리턴값&onPostExecute파라미터타입>

            @Override
            protected String doInBackground(String... urls) {


                try {

                    boolean itemTag=false;
                    boolean titleTag=false;
                    boolean companyTag=false;
                    boolean pubDateTag=false;
                    boolean linkTag=false;

                    URL url= new URL("https://news.google.com/rss/search?q=%EB%B9%84%EA%B1%B4&hl=ko&gl=KR&ceid=KR:ko");
                    InputStream in= url.openStream();


                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(in, "UTF-8");


                    int eventType =parser.getEventType();

                    while (eventType !=XmlPullParser.END_DOCUMENT) {//xml문서의 끝인가요?
                        switch (eventType) {

                            case XmlPullParser.START_DOCUMENT://파일시작?
                                itemArrayList = new ArrayList<news_item>();
                                break;

                            case XmlPullParser.END_DOCUMENT://파일끝?
                                break;

                            case XmlPullParser.END_TAG://태그 끝나묜
                                if(parser.getName().equals("item")&&news != null) {
                                    itemArrayList.add(news);
                                }
                                break;

                            case XmlPullParser.START_TAG://태그시작!
                                if (parser.getName().equals("item")){//시작태그가 item일경우
                                    news = new news_item();
                                    itemTag = true;
                                }
                                if (parser.getName().equals("title"))//시작태그가 title일때,,,
                                    titleTag = true;//titleTag값을 true로!
                                else if (parser.getName().equals("source"))//시작태그가 source일때
                                    companyTag = true;//companytag값을 true로
                                else if (parser.getName().equals("pubDate"))//시작태그가 pubDate일때
                                    pubDateTag = true;//pubDate값을 true로
                                else if (parser.getName().equals("link"))//시작태그가 pubDate일때
                                    linkTag = true;//pubDate값을 true로
                                break;

                            case XmlPullParser.TEXT:
                                if(itemTag) {
                                    if (titleTag) {
                                        //if(parser.getText().equals("\"비건\" - Google 뉴스"))
                                        news.setTitle(parser.getText());
                                        //Log.e("title", parser.getText());
                                        titleTag = false;
                                    } else if (companyTag) {
                                        news.setCompany(parser.getText());
                                        //Log.e("source",parser.getText());
                                        companyTag = false;
                                    } else if (pubDateTag) {
                                        news.setPubDate(parser.getText());
                                        //Log.e("pubDate", parser.getText());
                                        pubDateTag = false;
                                    } else if (linkTag) {
                                        news.setLink(parser.getText());
                                        //Log.e("link", parser.getText());
                                        linkTag = false;
                                    }
                                    break;
                                }
                        }
                        eventType = parser.next();
                    }
                    in.close();
                    return null;
                }catch (IOException e) {//에러1
                    e.printStackTrace();
                    return "IOException error";
                } catch (XmlPullParserException e) {//에러2
                    return "XmlPullParserException error";
                }

            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                //어댑터연결
                NewsAdapter adapter = new NewsAdapter(getActivity(),itemArrayList);
                recyclerView.setAdapter(adapter);

            }

            private InputStream downloadUrl(String urlString) throws IOException {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                return conn.getInputStream();
            }
        }

        //news_getXML실행하는 코드
        news_getXML myAsyncTask = new news_getXML();
        myAsyncTask.execute();

        return root;
    }

}
