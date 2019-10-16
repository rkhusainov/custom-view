package com.khusainov.rinat.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class FinanceProgressView extends View {

    private static final String TAG = "FinanceProgressView";

    public static final int MAX_PROGRESS = 100;
    public static final float START_ANGLE = -90f;
    public static final int MAX_ANGLE = 360;


    private int mProgress;
    private int mBackCircleColor;
    private int mColor;
    private int mTextSize;
    private int mStrokeWidth;

    private Paint mBackCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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

        canvas.translate(mStrokeWidth / 2, mStrokeWidth / 2);
        updateProgressRect();
        canvas.drawArc(mProgressRect, START_ANGLE, MAX_ANGLE, false, mBackCirclePaint);
        canvas.drawArc(mProgressRect, START_ANGLE, mProgress * MAX_ANGLE / MAX_PROGRESS, false, mCirclePaint);
        drawText(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + MeasureSpec.toString(widthMeasureSpec) + "], heightMeasureSpec = [" + MeasureSpec.toString(heightMeasureSpec) + "]");

        getTextBounds(formatString(MAX_PROGRESS));
        int requestedSize = (int) (Math.max(mTextBounds.width(), mTextBounds.height()) + Math.PI * mStrokeWidth);
        final int suggestedMinimumSize = Math.max(getSuggestedMinimumHeight(), getSuggestedMinimumWidth());
        requestedSize = Math.max(suggestedMinimumSize, requestedSize);
        final int resolveWidth = resolveSize(requestedSize + getPaddingLeft() + getPaddingRight(), widthMeasureSpec);
        final int resolveHeight = resolveSize(requestedSize + getPaddingTop() + getPaddingBottom(), heightMeasureSpec);
        final int resolvedSize = Math.min(resolveHeight, resolveWidth);
        setMeasuredDimension(resolvedSize, resolvedSize);
    }

    private void updateProgressRect() {
        mProgressRect.left = getPaddingLeft();
        mProgressRect.top = getPaddingTop();
        mProgressRect.right = getWidth() - mStrokeWidth - getPaddingRight();
        mProgressRect.bottom = getHeight() - mStrokeWidth - getPaddingBottom();
    }

    private void drawText(Canvas canvas) {
        final String progressString = formatString(mProgress);
        getTextBounds(progressString);
        float x = mProgressRect.width() / 2f - mTextBounds.width() / 2f - mTextBounds.left + mProgressRect.left;
        float y = mProgressRect.height() / 2f - mTextBounds.height() / 2f - mTextBounds.bottom + mProgressRect.top;
        canvas.drawText(progressString, x, y, mTextPaint);
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
        configureBackCirclePaint();
        configureCirclePaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColor);
    }

    private void configureBackCirclePaint() {
        mBackCirclePaint.setStrokeWidth(mStrokeWidth);
        mBackCirclePaint.setStyle(Paint.Style.STROKE);
        mBackCirclePaint.setColor(mBackCircleColor);
    }

    private void configureCirclePaint() {
        mCirclePaint.setStrokeWidth(mStrokeWidth);
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
            mBackCircleColor = typedArray.getColor(R.styleable.FinanceProgressView_backCircleColor, ContextCompat.getColor(getContext(), R.color.colorAccent));
            mColor = typedArray.getColor(R.styleable.FinanceProgressView_color, ContextCompat.getColor(getContext(), R.color.colorAccent));
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.FinanceProgressView_textSize,
                    getResources().getDimensionPixelSize(R.dimen.default_text_size));
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.FinanceProgressView_strokeWidth, getResources().getDimensionPixelSize(R.dimen.default_stroke_width));
        } finally {
            typedArray.recycle();
        }
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
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
