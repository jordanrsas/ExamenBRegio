package com.banregio.examenbregio.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.interfaces.enums.WelcomeImagesEnum;

/**
 * Created by jordan on 16/03/2018.
 */

public class WelcomeViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Context context;

    public WelcomeViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.welcome_slide, container, false);
        TextView textView = view.findViewById(R.id.txtWelcome);
        ImageView gifImageView = view.findViewById(R.id.gifWelcome);
        WelcomeImagesEnum welcomeImagesEnum = WelcomeImagesEnum.getItemById(position);
        textView.setText(welcomeImagesEnum.getText());
        textView.setTextColor(ContextCompat.getColor(context, welcomeImagesEnum.getColor()));

        gifImageView.setImageResource(welcomeImagesEnum.getImage());
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return WelcomeImagesEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
