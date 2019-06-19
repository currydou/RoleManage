package com.example.administrator.rolemanage.utils.organized;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    public static final int KEYBOARD_DELAYED_TIME = 150;

    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View currentFocusView = activity.getCurrentFocus();
        IBinder windowToken;
        InputMethodManager mMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mMethodManager != null
                && currentFocusView != null
                && (windowToken = currentFocusView.getApplicationWindowToken()) != null) {
            mMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

}
