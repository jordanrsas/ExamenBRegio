package com.banregio.examenbregio.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.fragments.BanregioFragment;
import com.banregio.examenbregio.fragments.GenericFragment;
import com.banregio.examenbregio.fragments.SucursalesFragment;
import com.banregio.examenbregio.interfaces.DialogDoubleActions;
import com.banregio.examenbregio.interfaces.MainManager;
import com.banregio.examenbregio.interfaces.enums.Direction;
import com.banregio.examenbregio.utils.ImageUtils;
import com.banregio.examenbregio.utils.SharedPreferencesKeys;
import com.banregio.examenbregio.utils.SharedPrefsManager;
import com.banregio.examenbregio.utils.SupportComponent;
import com.banregio.examenbregio.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jordan on 16/03/2018.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainManager {

    int lastCheked;
    SupportComponent mSupportComponent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    private CircleImageView imageUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSupportComponent = new SupportComponent(getSupportFragmentManager());
        SharedPrefsManager.initialize(this);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        imageUser = header.findViewById(R.id.imageProfile);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        String path = SharedPrefsManager.getInstance().getString(SharedPreferencesKeys.PHOTO_PATH);
        if (!TextUtils.isEmpty(path)) {
            ImageUtils.setPic(path, imageUser);
        }

        lastCheked = navigationView.getMenu().getItem(0).getItemId();
        navigationView.setCheckedItem(lastCheked);


        loadFragment(BanregioFragment.newInstance(), Direction.NONE, false);

        if (SharedPrefsManager.getInstance().getBoolean(SharedPreferencesKeys.IS_SECOUND_TIME_OPEN_APP)) {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            showDialogOut();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        drawerLayout.closeDrawers();
        if (lastCheked != id) {
            switch (id) {
                case R.id.menu_home:
                    loadFragment(BanregioFragment.newInstance(), Direction.FORDWARD, false);
                    lastCheked = id;
                    break;
                case R.id.menu_sucursales:
                    loadFragment(SucursalesFragment.newInstance(), Direction.FORDWARD, false);
                    lastCheked = id;
                    break;
                case R.id.menu_datos:
                    lastCheked = id;
                    break;
                case R.id.menu_logout:
                    UI.createSimpleCustomDialog(getString(R.string.desea_salir), getSupportFragmentManager(),
                            new DialogDoubleActions() {
                                @Override
                                public void actionConfirm(Object... params) {
                                    logOut();
                                }

                                @Override
                                public void actionCancel(Object... params) {
                                    navigationView.setCheckedItem(lastCheked);
                                }
                            }, true, true);
                    break;
            }
        }
        return true;
    }

    @Override
    public void showError(int msg) {

    }

    private void logOut() {
        SharedPrefsManager.getInstance().setBoolean(SharedPreferencesKeys.IS_LOGGED, false);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showDialogOut() {
        UI.createSimpleCustomDialog(getString(R.string.desea_cacelar), getSupportFragmentManager(), new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                finish();
            }

            @Override
            public void actionCancel(Object... params) {

            }
        }, true, true);
    }

    @Override
    public void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction, boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, R.id.container, Direction, addToBackStack);
    }


}
