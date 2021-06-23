package com.example.vegeproject.barcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.vegeproject.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.StringTokenizer;

public class result_fragment extends Fragment {

    TextView productName;
    TextView productintro;
    ImageView productImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_barcode,container,false);

        productName = root.findViewById(R.id.productname);
        productintro = root.findViewById(R.id.productintro);
        productImage = root.findViewById(R.id.productimage);

//        String url = "http://fresh.haccp.or.kr/prdimg/1996/19960242067291/19960242067291-1.jpg";
//        Glide.with(this).load(url).into(productImage);

        return root;
    }
}
