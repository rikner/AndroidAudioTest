package com.flowkey.flowaudiolab.VolumeMeter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by erik on 13.11.15.
 */
public class VolumeBar extends View {

    private static final String TAG = "VolumeBar";

    Paint paint = new Paint();

    private float volume = 20;

    int width = 100;

    public VolumeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        // float normalizedVolume = volume / (float)canvas.getHeight();
        float left = canvas.getWidth() / 2 - width;
        float top = canvas.getHeight() - volume;
        float right = canvas.getWidth() / 2 + width;
        float bottom = canvas.getHeight() + volume;
        canvas.drawRect(left, top, right, bottom, paint);


  /*      paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );*/
    }

    public void updateVolume(float volume) {
        Log.d(TAG, "native code executing callback with volume: " + Float.toString(volume));
        this.volume = 100 - volume;
        this.invalidate();
    }

}
