package com.banregio.examenbregio.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.fragments.GenericFragment;
import com.banregio.examenbregio.fragments.SucursalesFragment;
import com.banregio.examenbregio.interfaces.MainManager;
import com.banregio.examenbregio.interfaces.enums.Direction;
import com.banregio.examenbregio.utils.SupportComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jordan on 16/03/2018.
 */

public class SucursalesActivity extends AppCompatActivity implements MainManager {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SupportComponent mSupportComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);
        mSupportComponent = new SupportComponent(getSupportFragmentManager());

        ButterKnife.bind(this);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle(getString(R.string.sucursales_cajeros));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadFragment(SucursalesFragment.newInstance(), Direction.NONE, false);
    }

    @Override
    public void showError(int msg) {

    }

    @Override
    public void showDialogOut() {

    }

    @Override
    public void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction, boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, R.id.container, Direction, addToBackStack);
    }
}
