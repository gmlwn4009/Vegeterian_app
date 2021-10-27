package com.example.vegeproject.search;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.vegeproject.R;

public class ProgressDialog extends Dialog {
    public ProgressDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Dialog 제목 숨기기
        setContentView(R.layout.progress_dialog);
    }
}
