package com.example.administrator.rolemanage.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;

import com.example.administrator.rolemanage.R;


/**
 * Created by next on 2018/5/9.
 */

public class ProgressBar1 extends Dialog {

    public ProgressBar1(Context context) {
        this(context,0);
    }

    public ProgressBar1(Context context, int theme) {
        super(context, R.style.progressbar_translucent);
        init();
    }

    private void init() {
        setContentView(R.layout.progressbar2);
        Window window = getWindow();
        if (window != null) {
            window.getAttributes().gravity = Gravity.CENTER;
        }
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 屏蔽返回键，好像没用
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}