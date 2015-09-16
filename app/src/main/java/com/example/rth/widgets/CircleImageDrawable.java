package com.example.rth.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 */
public class CircleImageDrawable extends Drawable {

    private Paint paint;   //画笔
    private int width;     //图片的宽度
    private Bitmap bitmap;  //要修改的图片

    public CircleImageDrawable(Bitmap bitmap)
    {
        this.bitmap = bitmap ;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        width = Math.min(this.bitmap.getWidth(), this.bitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
    }

    @Override
    public int getIntrinsicWidth()
    {
        return width;
    }

    @Override
    public int getIntrinsicHeight()
    {
        return width;
    }

    @Override
    public void setAlpha(int alpha)
    {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }
}
