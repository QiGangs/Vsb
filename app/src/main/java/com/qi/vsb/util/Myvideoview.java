package com.qi.vsb.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by qigang on 16/7/28.
 */

class Myideoview extends VideoView {
    public Myideoview(Context context) {
        super(context);
    }

    public Myideoview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Myideoview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getWidth(), widthMeasureSpec);
        int height = getDefaultSize(getHeight(), heightMeasureSpec)/2+50;
        setMeasuredDimension(width, height);
    }
}
