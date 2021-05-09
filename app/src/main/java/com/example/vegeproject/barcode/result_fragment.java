package com.example.vegeproject.barcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.vegeproject.R;

import java.util.StringTokenizer;

public class result_fragment extends Fragment {

    TextView productName;
    ImageView productImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_barcode,container,false);
        Bundle bundle = getArguments();
        String output = bundle.getString("output");

        StringTokenizer arr = new StringTokenizer(output, "*");
        String[] attr = new String[6];
        for(int i=0;arr.hasMoreTokens();i++){
            attr[i] = arr.nextToken();
            Log.e("arr",attr[i]);
        }

        productName = root.findViewById(R.id.productname);
        productName.setText(attr[0]);

//        byte[] decodedByteArray = Base64.decode(attr[3], Base64.NO_WRAP);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray,0,attr[3].length());
//        Log.e("bitmap",String.valueOf(decodedByteArray));
//        productImage = root.findViewById(R.id.productimage);
//        productImage.setImageBitmap(bitmap);

        return root;
    }
}
