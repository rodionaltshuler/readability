package com.ottamotta.readability.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class StrechHeightSquareImageView extends ImageView {

    public StrechHeightSquareImageView(Context context) {
        super(context);
    }

    public StrechHeightSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StrechHeightSquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
