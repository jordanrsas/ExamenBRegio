package com.banregio.examenbregio.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.adapters.WelcomeViewPagerAdapter;
import com.banregio.examenbregio.interfaces.enums.WelcomeImagesEnum;
import com.banregio.examenbregio.utils.SharedPreferencesKeys;
import com.banregio.examenbregio.utils.SharedPrefsManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jordan on 16/03/2018.
 */

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    TextView[] dots;
    static int slides = WelcomeImagesEnum.values().length;
    WelcomeViewPagerAdapter myViewPagerAdapter;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrefsManager.initialize(this);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_wellcome);
        ButterKnife.bind(this);

        addBottomDots(0);
        changeStatusBarColor();

        myViewPagerAdapter = new WelcomeViewPagerAdapter(this);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        btnSkip.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        addBottomDots(position);

        if (position == slides - 1) {
            btnNext.setText(getString(R.string.start));
            btnSkip.setVisibility(View.GONE);
        } else {
            btnNext.setText(getString(R.string.next));
            btnSkip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                int current = getItem(+1);
                if (current < slides) {
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
                break;
            case R.id.btn_skip:
                launchHomeScreen();
                break;
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        SharedPrefsManager.getInstance().setBoolean(SharedPreferencesKeys.IS_SECOUND_TIME_OPEN_APP, true);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slides];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
