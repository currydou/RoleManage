package com.example.administrator.rolemanage.utils.organized;


import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;
import java.util.Collections;


public class SpannableStringUtils {

    public static class SpanText {
        public String text;
        public String linkUrl;
        public Context mmdLinkContext;

        public float relativeFontSize = 1.0f;
        public int textColor = Color.BLACK;
        public int backgroundColor = Color.TRANSPARENT;
        public boolean isNeedUnderLine = false;
        public boolean isNeedStrike = false;

        /**
         * 默认构造函数
         */
        public SpanText() {

        }

        /**
         * @param textd
         * @param colord
         * @param relativeSize 构造函数，初始化text，颜色和字体大小
         */
        public SpanText(String textd, int colord, float relativeSize) {
            this.text = textd;
            this.textColor = colord;
            if (relativeSize <= 0.0f) {
                relativeSize = 1.0f;
            }
            this.relativeFontSize = relativeSize;
        }

        /**
         * @return 对象转换成SpannableString
         */
        public SpannableString toSpanString() {
            if (StringUtils.isEmpty(text)) {
                return null;
            }
            SpannableString sb = new SpannableString(text);
            int spanStyle = SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE;
            sb.setSpan(new RelativeSizeSpan(this.relativeFontSize), 0, sb.length(), spanStyle);
            sb.setSpan(new ForegroundColorSpan(this.textColor), 0, sb.length(), spanStyle);

            if (this.backgroundColor != Color.TRANSPARENT) {
                sb.setSpan(new BackgroundColorSpan(this.backgroundColor), 0, sb.length(), spanStyle);
            }

            if (this.isNeedStrike) {
                sb.setSpan(new StrikethroughSpan(), 0, sb.length(), spanStyle);
            }

            if (this.isNeedUnderLine) {
                sb.setSpan(new UnderlineSpan(), 0, sb.length(), spanStyle);
            }
            return sb;
        }
    }

    /**
     * @param moreSpanText 可变长SpanText 组装SpanString
     * @return
     */
    public static SpannableStringBuilder makeSpannableString(SpanText... moreSpanText) {

        if (moreSpanText == null || moreSpanText.length == 0) {
            return null;
        }
        ArrayList<SpanText> textList = new ArrayList<SpanText>();
        Collections.addAll(textList, moreSpanText);
        return makeSpannableString(textList);
    }

    /**
     * @param textList SpanText数组 组装SpanString
     * @return
     */
    public static SpannableStringBuilder makeSpannableString(ArrayList<SpanText> textList) {
        if (textList.size() > 0) {
            SpannableStringBuilder ret = new SpannableStringBuilder();
            for (SpanText text : textList) {
                if (text != null) {
                    ret.append(text.toSpanString());
                }
            }

            return ret;
        }
        return null;
    }

    /**
     * 获取color的Id
     *
     * @param cId
     * @return
     */
    public static int getColorById(Context context, int cId) {
        return context.getResources().getColor(cId);
    }
}
