package com.banregio.examenbregio.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.banregio.examenbregio.fragments.GenericFragment;
import com.banregio.examenbregio.interfaces.enums.Direction;

import java.util.List;

/**
 * Created by jordan on 16/03/2018.
 */

public class SupportComponent {

    protected FragmentManager fragmentManager;
    GenericFragment lastFragment;
    @IdRes
    private int containerID;

    public SupportComponent(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        containerID = -1;
    }

    public void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                             boolean addToBackStack) {

        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (direction.equals(Direction.FORDWARD)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.BACK.getEnterAnimation(), Direction.BACK.getExitAnimation());
        } else if (direction.equals(Direction.BACK)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.FORDWARD.getEnterAnimation(), Direction.FORDWARD.getExitAnimation());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        lastFragment = fragment;
        fragmentTransaction.replace(idContainer, fragment, fragment.getFragmentTag()).commitAllowingStateLoss();

    }


    public void loadFragment(@NonNull Fragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                             boolean addToBackStack) {

        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (direction.equals(Direction.FORDWARD)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.BACK.getEnterAnimation(), Direction.BACK.getExitAnimation());
        } else if (direction.equals(Direction.BACK)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.FORDWARD.getEnterAnimation(), Direction.FORDWARD.getExitAnimation());
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(idContainer, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();

    }

    protected void removeLastFragment() {
        if (lastFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(lastFragment).commit();
        }
    }

    public Fragment getCurrentFragment() {
        if (containerID != -1) {
            return getCurrentFragment(containerID);
        }
        return null;
    }

    public Fragment getCurrentFragment(@IdRes int idContainer) {
        return fragmentManager.findFragmentById(idContainer);
    }

    protected List<Fragment> getFragments() {
        return fragmentManager.getFragments();
    }
}
