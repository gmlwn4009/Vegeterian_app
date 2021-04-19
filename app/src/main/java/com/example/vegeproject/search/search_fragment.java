package com.example.vegeproject.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vegeproject.MainActivity;
import com.example.vegeproject.R;

public class search_fragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        return root;
    }
}
