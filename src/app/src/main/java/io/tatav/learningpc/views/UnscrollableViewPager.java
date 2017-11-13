package io.tatav.learningpc.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Tatav on 24/02/2017.
 */

public final class UnscrollableViewPager extends ViewPager {
  private boolean isPagingEnabled = true;

  public UnscrollableViewPager(Context context) {
    super(context);
  }

  public UnscrollableViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onTouchEvent(event);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onInterceptTouchEvent(event);
  }

  public void setPagingEnabled(boolean enabled) {
    this.isPagingEnabled = enabled;
  }
}
