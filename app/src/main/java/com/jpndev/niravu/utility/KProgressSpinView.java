package com.jpndev.niravu.utility;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jpndev.niravu.R;
import com.kaopiz.kprogresshud.Indeterminate;

public class KProgressSpinView extends ImageView implements Indeterminate {

	private float mRotateDegrees;
	private int mFrameTime;
	private boolean mNeedToUpdateView;
	private Runnable mUpdateViewRunnable;

	public KProgressSpinView(Context context) {
		super(context);
		init();
	}

	public KProgressSpinView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setImageResource(R.drawable.kprogresshud_spinner);
		mFrameTime = 1000 / 12;
		mUpdateViewRunnable = new Runnable() {
			@Override
			public void run() {
				mRotateDegrees += 30;
				mRotateDegrees = mRotateDegrees < 360 ? mRotateDegrees : mRotateDegrees - 360;
				invalidate();
				if (mNeedToUpdateView) {
					postDelayed(this, mFrameTime);
				}
			}
		};
	}

	@Override
	public void setAnimationSpeed(float scale) {
		mFrameTime = (int) (1000 / 12 / scale);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(mRotateDegrees, getWidth() / 2, getHeight() / 2);
		super.onDraw(canvas);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mNeedToUpdateView = true;
		post(mUpdateViewRunnable);
	}

	@Override
	protected void onDetachedFromWindow() {
		mNeedToUpdateView = false;
		super.onDetachedFromWindow();
	}
}

