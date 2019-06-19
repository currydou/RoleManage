package com.example.administrator.rolemanage.utils.other;

import android.text.TextWatcher;

/**
 * Created by next on 2018/1/14.
 */

public abstract class TextWatcherOnlyAfter implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
