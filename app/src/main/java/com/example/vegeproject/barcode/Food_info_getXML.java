package com.example.vegeproject.barcode;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Food_info_getXML extends AsyncTask<String, Void, String> {

    public interface FoodResponse {
        void processFinish(String output);
    }

    public FoodResponse delegate = null;

    protected String doInBackground(String... urls) {
        try {
            String PrdlstNm = " ";
            String Prdkind = " ";
            String Allergy = " ";
            String Rawmtrl = " ";
            String Imgurl1 = " ";
            String Capacity = " ";
            String text = null;

            Boolean prdkind = Boolean.FALSE;
            Boolean prdlstNm = Boolean.FALSE;
            Boolean rawmtrl = Boolean.FALSE;
            Boolean allergy = Boolean.FALSE;
            Boolean imgurl1 = Boolean.FALSE;
            Boolean capacity = Boolean.FALSE;

            InputStream stream = downloadUrl(urls[0]);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(stream, "UTF-8");

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("prdlstNm"))
                            prdlstNm=Boolean.TRUE;
                        else if(parser.getName().equals("rawmtrl"))
                            rawmtrl = Boolean.TRUE;
                        else if(parser.getName().equals("allergy"))
                            allergy = Boolean.TRUE;
                        else if(parser.getName().equals("prdkind"))
                            prdkind = Boolean.TRUE;
                        else if(parser.getName().equals("capacity"))
                            capacity = Boolean.TRUE;
                        else if(parser.getName().equals("imgurl1"))
                            imgurl1 = Boolean.TRUE;
                        break;
                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        if(prdlstNm) {
                            PrdlstNm = parser.getText();
                            prdlstNm =Boolean.FALSE;
                        }
                        else if(rawmtrl) {
                            Rawmtrl = parser.getText();
                            rawmtrl =Boolean.FALSE;
                        }
                        else if(allergy) {
                            Allergy = parser.getText();
                            allergy =Boolean.FALSE;
                        }
                        else if(prdkind) {
                            Prdkind = parser.getText();
                            prdkind =Boolean.FALSE;
                        }
                        else if(capacity) {
                            Capacity = parser.getText();
                            capacity =Boolean.FALSE;
                        }
                        else if(imgurl1) {
                            Imgurl1 = parser.getText();
                            imgurl1 =Boolean.FALSE;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("response"))
                            break;
                }
                eventType = parser.next();
            }
            stream.close();

            return PrdlstNm + "*" + Rawmtrl + "*" + Allergy + "*" + Prdkind + "*" + Capacity + "*" + Imgurl1;
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException error";
        } catch (XmlPullParserException e) {
            return "XmlPullParserException error";
        }
    }

    protected void onPostExecute(String output) {
        Log.e("string",output);
        delegate.processFinish(output);
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
