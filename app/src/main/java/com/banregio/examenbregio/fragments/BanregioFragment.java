package com.banregio.examenbregio.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banregio.examenbregio.R;

/**
 * Created by jordan on 16/03/2018.
 */

public class BanregioFragment extends GenericFragment {

    public static BanregioFragment newInstance() {
        BanregioFragment fragment = new BanregioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_banregio, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {

    }
}
