package com.hiwhitley.potatoandtomato.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hiwhitley.potatoandtomato.R;

/**
 * Created by hiwhitley on 2016/4/7.
 */
public class HiTextView extends TextView {

    private String font;

    public HiTextView(Context context) {
        this(context, null);
    }

    public HiTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public HiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HiTextView, 0, 0);
        try {
            font = typedArray.getString(R.styleable.HiTextView_htv_typeface);
        } finally {
            typedArray.recycle();
        }

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                font);
        setTypeface(tf);
    }

}
