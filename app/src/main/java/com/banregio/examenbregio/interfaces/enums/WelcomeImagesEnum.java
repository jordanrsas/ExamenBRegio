package com.banregio.examenbregio.interfaces.enums;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.interfaces.BaseImagesEnum;

import static com.banregio.examenbregio.R.color.blanco;

/**
 * Created by jordan on 16/03/2018.
 */
@SuppressWarnings("ResourceAsColor")
public enum WelcomeImagesEnum implements BaseImagesEnum {


    SLIDE_1(1, R.drawable.banregio_logo, R.string.txt_slide_1, R.color.colorPrimary),

    SLIDE_2(2, R.drawable.banregio_logo, R.string.txt_slide_2, R.color.dot_dark_screen1),

    SLIDE_3(3, R.drawable.banregio_logo, R.string.txt_slide_3, R.color.dot_dark_screen3);

    private int image;
    private int text;
    private int id;
    private int color;

    WelcomeImagesEnum(int id, @DrawableRes int image, @StringRes int text, @ColorInt int color) {
        this.id = id;
        this.image = image;
        this.text = text;
        this.color = color;
    }

    public static WelcomeImagesEnum getItemById(int id) {
        return WelcomeImagesEnum.values()[id];
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int getImage() {
        return image;
    }

    @Override
    public int getText() {
        return text;
    }
}
