package com.example.vegeproject.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.search.FirebaseList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.vegeproject.R;

import java.util.StringTokenizer;

public class BarcodeActivity extends AppCompatActivity implements Barcode_getXML.BarcodeResponse, Food_info_getXML.FoodResponse {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");
    private FirebaseList firebaseList = new FirebaseList();
    Barcode_getXML barcode_getXML = new Barcode_getXML();
    Food_info_getXML food_info_getXML = new Food_info_getXML();
    private IntentIntegrator Scan;
    static String string;
    int i=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        result_fragment fragment = new result_fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.barcode_activity, fragment).commit();
//        Scan = new IntentIntegrator(this);
//        Scan.setOrientationLocked(false);//폰 방향대로 가로세로모드변경
//        Scan.setPrompt("바코드를 카메라 중앙에 맞춰주세요.");
//        Scan.initiateScan();

    }

    private void readFirebaseList(String searchItem){

        databaseReference.orderByChild("barcode").equalTo(searchItem).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    firebaseList.prdlstNm = data.child("prdlstNm").getValue(String.class);
                    firebaseList.allergy = data.child("allergy").getValue(String.class);
                    firebaseList.barcode = data.child("barcode").getValue(String.class);
                    Log.w("FirebaseData", firebaseList.prdlstNm + ", " + firebaseList.allergy + ", " + firebaseList.barcode);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //바코드값 result에 받기
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            //뒤로가기 시 바코드 종료 후 mainActivity로 돌아감
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
            //바코드 정보 입력성공 시
             else {
                Toast.makeText(this, "바코드값: " + result.getContents(), Toast.LENGTH_LONG).show();
                string=result.getContents();

//                barcode_getXML.delegate = this;
//                String barcodeUrl = "https://openapi.foodsafetykorea.go.kr/api/792583e106f84aa688f1/C005/xml/0/1/BAR_CD=" +
//                        result.getContents();
//                barcode_getXML.execute(barcodeUrl);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void processFinish(String output) {
        // i==0일 때 barcode XML로 부터 품목번호 받아온 뒤 food_XML url에 대입
//        if(i==0) {
//            food_info_getXML.delegate = this;
//            String foodUrl = "http://apis.data.go.kr/B553748/CertImgListService/getCertImgListService?ServiceKey=kVYcCisbHyjiLHSoknw1iZbhenW6Glc2mM4hfGf1EeIHjXagq6P9g98eMXs6lFGtlksA74tis6Z677Ol%2FjiHrw%3D%3D&prdlstReportNo=" +
//                    output;
//            food_info_getXML.execute(foodUrl);
//            i++;
//
//            StringTokenizer arr = new StringTokenizer(output, "*");
//            String[] attr = new String[6];
//            for(int i=0;arr.hasMoreTokens();i++){
//                attr[i] = arr.nextToken();
//                Log.e("arr",attr[i]);
//            }
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance("https://vegeproject-7e016-default-rtdb.asia-southeast1.firebasedatabase.app/");
//            DatabaseReference myRef = database.getReference("880123153416");
//
//            myRef.child("제품명").setValue("돈까스");
//            myRef.child("이미지").setValue("url::///");
//        }
//        // food_XML에서 받아온 정보 표시
//        else{
//            result_fragment fragment = new result_fragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.barcode_activity, fragment).commit();
//
//            Bundle bundle = new Bundle();
//            bundle.putString("output",output);
//            fragment.setArguments(bundle);
//            i--;
//        }
//        Log.e("process string",output);
    }
}
