/**
 * Copyright  All right reserved by IZHUO.NET.
 */
package com.example.administrator.rolemanage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.example.administrator.rolemanage.R;


/**
 * 有清除按钮；不允许输入表情；
 * <net.izhuo.app.base.view.ClearEditText
 * android:id="@+id/et_content"
 * android:layout_width="match_parent"
 * android:layout_height="44dp"
 * android:background="@drawable/selector_login_edit_border"
 * android:drawablePadding="5dp"
 * android:hint=""
 * android:paddingLeft="10dp"
 * android:paddingRight="10dp"
 * android:singleLine="true"
 * android:textColor="#333"
 * android:textColorHint="#dddddd"
 * android:textSize="16sp" />
 */
@SuppressWarnings("JavaDoc")
public class ClearEditText extends AppCompatEditText implements OnFocusChangeListener,TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;
    private OnFocusChangeListener mFocusChangeListener;
    private TextWatcher mTextWatcher;
    private String text = "";
    private boolean mUseClearIcon = true;
    private boolean mSupportEmoJi = false;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttribute(context, attrs, defStyle);
        init();
    }

    private void initAttribute(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SearchViewItem, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SearchViewItem_useClearIcon:
                    mUseClearIcon = a.getBoolean(attr,true);
                    break;
                case R.styleable.SearchViewItem_supportEmoJi:
                    mSupportEmoJi = a.getBoolean(attr,true);
                    break;
            }
        }
        a.recycle();
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            // throw new
            // NullPointerException("You can add drawableRight attribute in XML");
            //noinspection deprecation
            mClearDrawable = getResources().getDrawable(R.drawable.edit_clear);
        }

        assert mClearDrawable != null;
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        super.setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        super.addTextChangedListener(this);
    }
    /*-----------------------------------------清除按钮---------------------------------------------*/

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
     * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //Log.e("MotionEvent:","MotionEvent ACTION_UP=="+getCompoundDrawables()[2]);
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                // Log.e("ClearEditText:","touchable=="+touchable);
                if (touchable && !TextUtils.isEmpty(getText())) {
                    getText().clear();
                    // setShakeAnimation();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        this.mFocusChangeListener = l;
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mFocusChangeListener != null) {
            mFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        if (mUseClearIcon) {
            Drawable right = visible ? mClearDrawable : null;
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        }

    }

    public void addTextChangedListener(TextWatcher watcher) {
        this.mTextWatcher = watcher;
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
        if (mTextWatcher != null) {
            mTextWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mTextWatcher != null) {
            mTextWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mTextWatcher != null) {
            mTextWatcher.afterTextChanged(editable);
        }
        /*------------------------------------不允许输入表情-----------------------------------*/
        if (!mSupportEmoJi && text.length() < editable.toString().length() && !TextUtils.equals(text, editable.toString())) {
            String diff = editable.toString().replace(text, "");
            if (containsEmoji(diff)) {
                //ToastUtils.showToast(R.string.unsupported_expression);
                //是表情符号就将文本还原为输入表情符号之前的内容
                setText(text);
                CharSequence text = getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
            } else {
                text = editable.toString();
            }
        } else {
            text = editable.toString();
        }
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
    /*-----------------------------------------清除按钮---------------------------------------------*/
    /*-----------------------------------------set/get---------------------------------------------*/

    public void setUseClearIcon(boolean useClearIcon) {
        this.mUseClearIcon = useClearIcon;
    }

    public void setSupportEmoJi(boolean supportEmoJi) {
        this.mSupportEmoJi = supportEmoJi;
    }
    /*-----------------------------------------不允许输入表情---------------------------------------------*/
    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
    /*-----------------------------------------不允许输入表情---------------------------------------------*/


}
