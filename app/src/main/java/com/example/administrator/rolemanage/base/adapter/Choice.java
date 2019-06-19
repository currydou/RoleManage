package com.example.administrator.rolemanage.base.adapter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by next on 2018/7/19.
 */

public interface Choice {

    int CHOICE_MODE_NONE = 0;
    int CHOICE_MODE_SINGLE = 1;
    int CHOICE_MODE_MULTI = 2;
    @IntDef({CHOICE_MODE_SINGLE, CHOICE_MODE_MULTI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ChoiceMode {
    }

    String getCheckedId();

    //可以不用
    void setCheckedId(String mCheckedId);
}
