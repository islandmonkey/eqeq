/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Aidan Fell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.islandmonkey.eqeq;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class VerticalSeekBar extends SeekBar {
	 
    public VerticalSeekBar(Context context) {
        super(context);
    }
 
    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }
 
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }
 
    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);
 
        super.onDraw(c);
    }
 
    private OnSeekBarChangeListener onChangeListener;
    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }
 
    private int lastProgress = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
 
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            onChangeListener.onStartTrackingTouch(this);
            setPressed(true);
            setSelected(true);
            break;
        case MotionEvent.ACTION_MOVE:
            super.onTouchEvent(event);
            int progress = getMax() - (int) (getMax() * event.getY() / getHeight());
             
            // Ensure progress stays within boundaries
            if (progress < 0) {
            	progress = 0;
            }
            if (progress > getMax()) {
            	progress = getMax();
            }
            setProgress(progress);  // Draw progress
            if (progress != lastProgress) {
                // Only enact listener if the progress has actually changed
                lastProgress = progress;
                onChangeListener.onProgressChanged(this, progress, true);
            }
             
            onSizeChanged(getWidth(), getHeight() , 0, 0);
            setPressed(true);
            setSelected(true);
            break;
        case MotionEvent.ACTION_UP:
            onChangeListener.onStopTrackingTouch(this);
            setPressed(false);
            setSelected(false);
            break;
        case MotionEvent.ACTION_CANCEL:
            super.onTouchEvent(event);
            setPressed(false);
            setSelected(false);
            break;
        }
        return true;
    }
 
    public synchronized void setProgressAndThumb(int progress) {
        setProgress(progress);
        onSizeChanged(getWidth(), getHeight() , 0, 0);
        if(progress != lastProgress) {
            // Only enact listener if the progress has actually changed
            lastProgress = progress;
            onChangeListener.onProgressChanged(this, progress, true);
        }
    }
 
    public synchronized void setMaximum(int maximum) {
        setMax(maximum);
    }
 
    public synchronized int getMaximum() {
        return getMax();
    }
}