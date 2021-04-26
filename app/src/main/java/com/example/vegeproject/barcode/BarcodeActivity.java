package com.example.vegeproject.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.vegeproject.R;

public class BarcodeActivity extends AppCompatActivity {

    private IntentIntegrator Scan;
    static String string;
    Button btn1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        btn1=(Button)findViewById(R.id.btn1);

        Scan = new IntentIntegrator(this);
        Scan.setOrientationLocked(false);//폰 방향대로 가로세로모드변경
        Scan.setPrompt("바코드를 카메라 중앙에 맞춰주세요.");
        Scan.initiateScan();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                result_fragment fragment1= new result_fragment();
                transaction.replace(R.id.frame,fragment1);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

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
