package com.pksoft.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pksoft.util.Rectangle;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class DrawView extends androidx.appcompat.widget.AppCompatImageView {
    List<Rectangle> areas = null;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (areas != null) {
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(10);
            for (Rectangle area : areas) {
                RectF r = new RectF(area.getP1().getX(), area.getP1().getY(), area.getP1().getX()+area.getWidth(), area.getP1().getY()+area.getHeight() );
                canvas.drawRect(r, paint);
            }
        }
    }

    public List<Rectangle> getAreas() {
        return areas;
    }

    public void setAreas(List<Rectangle> areas) {
        this.areas = areas;
    }
}
