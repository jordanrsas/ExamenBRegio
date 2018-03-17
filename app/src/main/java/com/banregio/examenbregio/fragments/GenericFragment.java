package com.banregio.examenbregio.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.banregio.examenbregio.interfaces.OnEventListener;

/**
 * Created by jordan on 16/03/2018.
 */

public abstract class GenericFragment extends Fragment {
    protected View rootview;
    protected OnEventListener onEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @NonNull
    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    public abstract void initViews();
}
