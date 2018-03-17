package com.banregio.examenbregio.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.interfaces.DialogDoubleActions;

import java.util.Calendar;

/**
 * Created by jordan on 15/03/2018.
 */

public class UI {

    public static void showDialogPicker(Context context, DatePickerDialog.OnDateSetListener listener) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, month, day);
        datePickerDialog.show();
    }

    public static void createSimpleCustomDialog(String message, FragmentManager fragmentManager) {
        createSimpleCustomDialog(message, fragmentManager, null, true, false);
    }

    public static void createSimpleCustomDialog(String message,
                                                FragmentManager fragmentManager, final DialogDoubleActions actions,
                                                boolean hasConfirmBtn, boolean hasCancelBtn) {
        createSimpleCustomDialog("", message, fragmentManager, actions, hasConfirmBtn, hasCancelBtn);
    }

    public static void createSimpleCustomDialog(String title, String message,
                                                FragmentManager fragmentManager, final DialogDoubleActions actions,
                                                boolean hasConfirmBtn, boolean hasCancelBtn) {
        final FragmentDialog apportaFragmentDialog = FragmentDialog.newInstance(R.layout.dialog_message_layout,
                title, message, hasConfirmBtn, hasCancelBtn);
        apportaFragmentDialog.setDialogActions(new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                apportaFragmentDialog.dismiss();
                if (actions != null) {
                    actions.actionConfirm(params);
                }
            }

            @Override
            public void actionCancel(Object... params) {
                apportaFragmentDialog.dismiss();
                if (actions != null) {
                    actions.actionCancel(params);
                }
            }
        });

        apportaFragmentDialog.setCancelable(false);
        apportaFragmentDialog.show(fragmentManager, FragmentDialog.class.getSimpleName());
    }

    public static void hideKeyBoard(Activity activity) {

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
