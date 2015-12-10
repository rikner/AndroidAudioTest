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

        float normalizedVolume = this.volume / canvas.getHeight();

        // canvas.drawRect(canvas.getWidth() / 2 - width, canvas.getHeight() - volume, canvas.getWidth() / 2 + width, canvas.getHeight() + volume, paint);

        float left = canvas.getWidth() / 2 - width;
        float top = canvas.getHeight() / 2 - volume;
        float right = canvas.getWidth() / 2 + width;
        float bottom = canvas.getHeight() / 2 + volume;
        canvas.drawRect(left, top, right, bottom, paint);

    }

    public void updateVolume(float volumePercentage) {

        this.volume = volumePercentage;
        // Log.d(TAG, "incoming volume: " + Float.toString(this.volume));
        this.invalidate();
    }

}
