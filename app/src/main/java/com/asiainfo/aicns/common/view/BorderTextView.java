package com.asiainfo.aicns.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.asiainfo.aicns.R;

/**
 * Created by uuom on 16-11-6.
 */
public class BorderTextView extends TextView {

    public static final float DEFAULT_BORDER_WIDTH = 1.0f;    // 默认边框宽度, 1dp
    public static final float DEFAULT_ROTATION = 2.0f;   // 默认圆角半径, 2dp
    public static final float DEFAULT_LR_PADDING = 6f;      // 默认左右内边距
    public static final float DEFAULT_TB_PADDING = 2f;      // 默认上下内边距

    private int borderWidth;    // 边框线宽
    private int borderColor;    // 边框颜色
    private int rotation;   // 圆角半径
    private boolean followTextColor; // 边框颜色是否跟随文字颜色

    private Paint paint = new Paint();
    private RectF rectF;

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        borderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DEFAULT_BORDER_WIDTH, displayMetrics);
        rotation = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ROTATION, displayMetrics);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView);
        borderWidth = ta.getDimensionPixelSize(R.styleable.BorderTextView_borderWidth, borderWidth);
        rotation = ta.getDimensionPixelSize(R.styleable.BorderTextView_rotation, rotation);
        borderColor = ta.getColor(R.styleable.BorderTextView_borderColor, Color.TRANSPARENT);
        followTextColor = ta.getBoolean(R.styleable.BorderTextView_followTextColor, true);
        ta.recycle();

        rectF = new RectF();

        // 如果使用时没有设置内边距, 设置默认边距
        int paddingLeft = getPaddingLeft() == 0 ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LR_PADDING, displayMetrics) : getPaddingLeft();
        int paddingRight = getPaddingRight() == 0 ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LR_PADDING, displayMetrics) : getPaddingRight();
        int paddingTop = getPaddingTop() == 0 ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TB_PADDING, displayMetrics) : getPaddingTop();
        int paddingBottom = getPaddingBottom() == 0 ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TB_PADDING, displayMetrics) : getPaddingBottom();
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE); //空心
        paint.setAntiAlias(true);
        paint.setStrokeWidth(borderWidth);


        if (followTextColor){
            borderColor = getCurrentTextColor();
        }
        paint.setColor(borderColor);

        rectF.left = rectF.top = 1f*borderWidth;
        rectF.right = getMeasuredWidth() - borderWidth;
        rectF.bottom = getMeasuredHeight() - borderWidth;
        canvas.drawRoundRect(rectF,rotation,rotation,paint);
    }
}
