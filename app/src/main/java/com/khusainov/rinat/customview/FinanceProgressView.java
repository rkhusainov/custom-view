package com.khusainov.rinat.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FinanceProgressView extends View {

    private static final String TAG = "FinanceProgressView";

    public static final int MAX_ANGLE = 360;
    private static final int MAX_PROGRESS = 100;
    private static final int DEFAULT_COLOR = Color.RED;
    public static final float STROKE_WIDTH = 64;

    // 64/100

    private int mProgress;
    private int mColor;
    private int mTextSize;

    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mProgressRect = new RectF();
    private Rect mTextBounds = new Rect();

    public FinanceProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public FinanceProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(STROKE_WIDTH / 2, STROKE_WIDTH / 2);
        canvas.drawArc(mProgressRect, -90f, mProgress * MAX_ANGLE / MAX_PROGRESS, false, mCirclePaint);
        final String progressString = formatString(mProgress);
        getTextBounds(progressString);
        float x = mProgressRect.width() / 2f - mTextBounds.width() / 2f - mTextBounds.left;
        float y = mProgressRect.height() / 2f + mTextBounds.height() / 2f - mTextBounds.bottom;
        canvas.drawText(progressString, x, y, mTextPaint)
        ;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + MeasureSpec.toString(widthMeasureSpec) + "], heightMeasureSpec = [" + MeasureSpec.toString(heightMeasureSpec) + "]");

        getTextBounds(formatString(MAX_PROGRESS));
        final int size = (int) (Math.max(mTextBounds.width(), mTextBounds.height()) + Math.PI * STROKE_WIDTH);
        final int width = resolveSize(size, widthMeasureSpec);
        final int height = resolveSize(size, heightMeasureSpec);
        mProgressRect.right = width - STROKE_WIDTH;
        mProgressRect.bottom = height - STROKE_WIDTH;


        setMeasuredDimension(width, height);

        // когда используем setMeasureDimension убираем super метод
    }

/*    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged() called with: w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
        mProgressRect.right = w - STROKE_WIDTH;
        mProgressRect.bottom = h-STROKE_WIDTH;
    }*/

    private void getTextBounds(String progressString) {
        mTextPaint.getTextBounds(progressString, 0, progressString.length(), mTextBounds);
    }

    private String formatString(int progress) {
        return String.format(getResources().getString(R.string.progress_template), progress);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        extractAttributes(context, attrs);
        configureCirclePaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColor);
    }

    private void configureCirclePaint() {
        mCirclePaint.setStrokeWidth(STROKE_WIDTH);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(mColor);
        mTextPaint.setTextSize(128);
    }

    private void extractAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final Resources.Theme theme = context.getTheme();
        final TypedArray typedArray = theme.obtainStyledAttributes(attrs, R.styleable.FinanceProgressView,
                R.attr.financeProgressStyle, 0);
        try {
            mProgress = typedArray.getInteger(R.styleable.FinanceProgressView_progress, 0);
            mColor = typedArray.getColor(R.styleable.FinanceProgressView_color, DEFAULT_COLOR);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.FinanceProgressView_textSize,
                    getResources().getDimensionPixelSize(R.dimen.default_text_size));
        } finally {
            typedArray.recycle();
        }
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }
}
