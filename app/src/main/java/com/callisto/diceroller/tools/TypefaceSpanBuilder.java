package com.callisto.diceroller.tools;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.TypedValue;
import android.widget.TextView;

import com.callisto.diceroller.application.App;

import io.github.inflationx.calligraphy3.CalligraphyTypefaceSpan;
import io.github.inflationx.calligraphy3.TypefaceUtils;

public class TypefaceSpanBuilder
{
    static public void setTypefacedTitle(
        TextView textView,
        String text,
        String font,
        int fontSize)
    {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder();

        sBuilder.append(text);

        // This fucker is CASE SENSITIVE: even the font extension being in lower letters will cause a crash
//        String font = Constants.Fonts.CEZANNE.getText();

        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(
            TypefaceUtils.load(
                App.getRes().getAssets(),
                font)
        );

        int length = text.length();

        sBuilder.setSpan(typefaceSpan, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(sBuilder, TextView.BufferType.SPANNABLE);

//        int fontSize = Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue();

        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            fontSize
        );
    }
}
