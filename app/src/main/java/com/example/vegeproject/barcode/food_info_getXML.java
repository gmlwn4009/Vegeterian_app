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

public class food_info_getXML extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... urls) {
        try {
            String name = " ";
            String number = " ";
            String Allergy = " ";
            String text = null;

            Boolean prdistReportNo = Boolean.FALSE;
            Boolean prdistNm = Boolean.FALSE;
            Boolean rawmtrl = Boolean.FALSE;
            Boolean allergy = Boolean.FALSE;
            Boolean imgurl1 = Boolean.FALSE;
            Boolean imgurl2 = Boolean.FALSE;

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
                        if(parser.getName().equals("prdistNM"))
                            prdistNm=Boolean.TRUE;
                        else if(parser.getName().equals("prdistReportNo"))
                            prdistReportNo = Boolean.TRUE;
                        else if(parser.getName().equals("allergy"))
                            allergy = Boolean.TRUE;
                        break;
                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        if(prdistNm) {
                            name = parser.getText();
                            prdistNm =Boolean.FALSE;
                        }
                        else if(prdistReportNo) {
                            number = parser.getText();
                            prdistReportNo =Boolean.FALSE;
                        }
                        else if(allergy) {
                            Allergy = parser.getText();
                            allergy =Boolean.FALSE;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("response"))
                            break;
                }
                eventType = parser.next();
            }
            stream.close();

            return name+" and "+number+" and "+Allergy;
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException error";
        } catch (XmlPullParserException e) {
            return "XmlPullParserException error";
        }
    }

    protected void onPostExecute(String s) {
        Log.e("string",s);
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
