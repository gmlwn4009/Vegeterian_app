package com.example.vegeproject.barcode;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vegeproject.search.FirebaseData;
import com.example.vegeproject.search.ProgressDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.vegeproject.R;

public class BarcodeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");
    private FirebaseData firebaseData;
    private IntentIntegrator Scan;
    public String searchItem; // 바코드 값

    public ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Scan = new IntentIntegrator(this);
        Scan.setOrientationLocked(false); // 폰 방향대로 가로세로 모드 변경
        Scan.setPrompt("바코드를 카메라 중앙에 맞춰주세요.");
        Scan.initiateScan();

        // 로딩창 객체 생성
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 바코드 값 result에 받기
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            // 뒤로가기 시 바코드 종료 후 mainActivity로 돌아감
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
            // 바코드 정보 입력성공 시
            else {
                searchItem = result.getContents();

                readFirebaseData(new MyCallback() {
                    @Override
                    public void onCallback(FirebaseData data) {
                        if(data==null){
                            progressDialog.dismiss();
                            Toast.makeText(BarcodeActivity.this, "등록되어 있지 않은 바코드 입니다.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(BarcodeActivity.this,BarcodeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            firebaseData = data;
                            Intent intent = new Intent(BarcodeActivity.this, BarcodeResult.class);
                            intent.putExtra("data", firebaseData); // 데이터 송신
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                });
            }
        }
    }

    public void readFirebaseData(MyCallback myCallback){

        databaseReference.orderByChild("barcode").equalTo(searchItem).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    firebaseData = new FirebaseData(data.child("prdlstNm").getValue(String.class),
                                                   data.child("barcode").getValue(String.class),
                                                   data.child("allergy").getValue(String.class),
                                                   data.child("imgurl").getValue(String.class),
                                                   data.child("prdkind").getValue(String.class));
                }
                myCallback.onCallback(firebaseData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public interface MyCallback {
        void onCallback(FirebaseData data);
    }
}
