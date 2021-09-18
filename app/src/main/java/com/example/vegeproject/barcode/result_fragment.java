package com.example.vegeproject.barcode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vegeproject.R;

public class result_fragment extends Fragment {

    TextView productName;
    TextView productintro;
    ImageView productImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_result,container,false);

        productName = root.findViewById(R.id.productname);
        productintro = root.findViewById(R.id.productintro);
        productImage = root.findViewById(R.id.productimage);

//        String url = "http://fresh.haccp.or.kr/prdimg/1996/19960242067291/19960242067291-1.jpg";
//        Glide.with(this).load(url).into(productImage);

        return root;
    }
}
