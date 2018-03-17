package com.banregio.examenbregio.interfaces;

import android.support.annotation.NonNull;

import com.banregio.examenbregio.fragments.GenericFragment;
import com.banregio.examenbregio.interfaces.enums.Direction;

/**
 * Created by jordan on 16/03/2018.
 */

public interface MainManager extends MainViewManager {
    void showDialogOut();

    void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction, boolean addToBackStack);
}
