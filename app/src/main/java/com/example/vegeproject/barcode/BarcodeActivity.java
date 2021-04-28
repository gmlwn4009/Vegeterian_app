package com.example.vegeproject.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.vegeproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BarcodeActivity extends AppCompatActivity {

    Activity tt = this;
    private IntentIntegrator Scan;
    static String string;
    Button btn1, btn2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scan = new IntentIntegrator(tt);
                Scan.setOrientationLocked(false);//폰 방향대로 가로세로모드변경
                Scan.setPrompt("바코드를 카메라 중앙에 맞춰주세요.");
                Scan.initiateScan();
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                result_fragment fragment1= new result_fragment();
                transaction.replace(R.id.frame,fragment1);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

//        String url2 = "https://openapi.foodsafetykorea.go.kr/api/792583e106f84aa688f1/C005/xml/0/1/BAR_CD=8809549135325";
//        new Barcode_getXML().execute(url2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //바코드값 result에 받기
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "바코드값: " + result.getContents(), Toast.LENGTH_LONG).show();
                string=result.getContents();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
