package com.qi.vsb.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.media.RatingCompat;
import android.util.AttributeSet;
import android.view.View;

import com.qi.vsb.view.Home;

/**
 * Created by qigang on 16/7/16.
 */

public class Chechview extends View {


    public Chechview(Context context) {
        super(context);
    }

    public Chechview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Chechview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int verticalCenter    =  getHeight() / 2;
        int horizontalCenter  =  getWidth() / 2;
        int circleRadius      = 220;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#00838f"));
        paint.setStrokeWidth(24);
        canvas.drawCircle( horizontalCenter, verticalCenter-0, circleRadius, paint);

//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.parseColor("#00838f"));
//        paint.setTextSize(200);
//        canvas.drawText("97",horizontalCenter-110,verticalCenter+70,paint);
    }
}
