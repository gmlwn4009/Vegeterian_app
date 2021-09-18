package com.example.vegeproject.search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vegeproject.R;

public class SearchResultClick extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView productname = findViewById(R.id.productname);
        ImageView productimage = findViewById(R.id.productimage);

        Intent intent = getIntent();
        productname.setText(intent.getStringExtra("prdlstNm"));
        Glide.with(this).load(intent.getStringExtra("imgUrl")).into(productimage);
    }
}
