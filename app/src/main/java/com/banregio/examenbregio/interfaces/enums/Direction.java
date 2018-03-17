package com.banregio.examenbregio.interfaces.enums;

import android.support.annotation.AnimRes;

import com.banregio.examenbregio.R;

/**
 * Created by jordan on 16/03/2018.
 */

public enum Direction {
    FORDWARD(R.anim.slide_from_right, R.anim.slide_to_left),
    BACK(R.anim.slide_from_left, R.anim.slide_to_right),
    NONE(0, 0);

    private int enterAnimation;
    private int exitAnimation;

    Direction(@AnimRes int enterAnimation, @AnimRes int exitAnimation) {
        this.enterAnimation = enterAnimation;
        this.exitAnimation = exitAnimation;
    }

    public int getEnterAnimation() {
        return enterAnimation;
    }

    public int getExitAnimation() {
        return exitAnimation;
    }
}
