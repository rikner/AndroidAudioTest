package com.flowkey.flowaudiolab.VolumeMeter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by erik on 13.11.15.
 */
public class VolumeBar extends View {

    Paint paint = new Paint();

    float left = 50;
    float top = 50;
    float right = 50;
    float bottom = 50;

    public void setVolume(float volume) {
        this.volume = volume;
    }

    float volume = 20;


    int x = 50;
    int y = 50;
    int width = 50;
    int height = 100;


    public VolumeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(canvas.getWidth() / 2 - width, canvas.getHeight() / 2 - height, canvas.getWidth() / 2 + width, canvas.getHeight() / 2 + height, paint );

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        canvas.drawRect(canvas.getWidth() / 2 - width, canvas.getHeight() / 2 - volume, canvas.getWidth() / 2 + width, canvas.getHeight() / 2 + volume, paint );


  /*      paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );*/
    }

}
