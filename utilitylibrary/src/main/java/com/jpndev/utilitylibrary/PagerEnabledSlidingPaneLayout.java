package com.jpndev.utilitylibrary;

import android.content.Context;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by ceino on 3/2/15.
 */
//SlidingPaneLayout
public class PagerEnabledSlidingPaneLayout extends WrappingSlidingPaneLayout {
    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mEdgeSlop;
    private int orientation;

    public PagerEnabledSlidingPaneLayout(Context context) {
        this(context, null);
    }

    public PagerEnabledSlidingPaneLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerEnabledSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        ViewConfiguration config = ViewConfiguration.get(context);
        mEdgeSlop = config.getScaledEdgeSlop();
    }

    public void setOrientation(int orientation) {

        this.orientation = orientation;
    }

 /*   @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width=Resources.getSystem().getDisplayMetrics().widthPixels;
        if(width>=0)
            widthMeasureSpec=width;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x = ev.getX();
                final float y = ev.getY();
                // The user should always be able to "close" the pane, so we only check
                // for child scrollability if the pane is currently closed.
                if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this, false,
                        Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {

                    // How do we set super.mIsUnableToDrag = true?

                    // send the parent a cancel event
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onInterceptTouchEvent(cancelEvent);
                }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }


}
