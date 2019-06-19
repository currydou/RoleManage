package com.example.administrator.rolemanage.utils.other;

import android.content.Context;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lib on 2017/7/26.
 */

public class ViewUtils {
    /**
     * 输入法弹出时将指定view顶上去
     *
     * @param root
     * @param scrollToView
     */
    public static void controlKeyboardLayout(final View root, final View scrollToView) {
        KeyboardChangeListener listener = new KeyboardChangeListener(root);
        listener.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                int[] location = new int[2];
                // 获取scrollToView在窗体的坐标
                scrollToView.getLocationInWindow(location);

                if (rect.bottom < location[1] + scrollToView.getHeight() && isShow) {
                    int srollHeight = (location[1] + scrollToView
                            .getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else if (!isShow) {
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * 设置hint大小
     *
     * @param size
     * @param v
     * @param res
     */
    public static void setViewHintSize(Context context, int size, TextView v, int res) {
        SpannableString ss = new SpannableString(context.getResources().getString(
                res));
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        v.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }

}
