package com.tinkoff.androidcourse;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class WorkerItemDecorator extends RecyclerView.ItemDecoration {

    private static final int COLORS[] = {0xFF176BEF, 0xFFFF3E30, 0xFFF7B529, 0xFF179C52};

    private final Paint paint = new Paint();

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        DisplayMetrics dm = parent.getResources().getDisplayMetrics();
        int height = parent.getResources().getDimensionPixelSize(R.dimen.decorator_height) / 2;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            float width = child.getRight() - child.getLeft() + child.getPaddingLeft() - child.getPaddingRight();
            float left = child.getLeft() + child.getTranslationX();
            int paddingLeft = child.getPaddingLeft();
            float bottom = child.getBottom() + child.getTranslationY();

            for (int j = 0; j < COLORS.length; j++) {
                paint.setColor(COLORS[j]);
                c.drawRect(left + paddingLeft + j * width / COLORS.length,
                        bottom,
                        left + paddingLeft + (j + 1) * width / COLORS.length,
                        bottom + height, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, 0, 0,
                parent.getResources().getDimensionPixelSize(R.dimen.decorator_height));
    }
}
