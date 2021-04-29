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

class Barcode_getXML extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... urls) {
        try {
            String name = " ";
            String number = " ";
            String text = null;
            Boolean PRDLST_NM=Boolean.FALSE;
            Boolean PRDLST_REPORT_NO=Boolean.FALSE;

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
                        if(parser.getName().equals("PRDLST_NM"))
                            PRDLST_NM=Boolean.TRUE;
                        else if(parser.getName().equals("PRDLST_REPORT_NO"))
                            PRDLST_REPORT_NO = Boolean.TRUE;
                        break;
                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        if(PRDLST_NM) {
                            name = parser.getText();
                            PRDLST_NM =Boolean.FALSE;
                        }
                        else if(PRDLST_REPORT_NO) {
                            number = parser.getText();
                            PRDLST_REPORT_NO =Boolean.FALSE;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("C005"))
                            break;
                }
                eventType = parser.next();
            }
            stream.close();

            return name+" "+number;
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
