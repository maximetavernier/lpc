package io.tatav.learningpc.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import io.tatav.learningpc.R;

/**
 * Created by Tatav on 09/03/2017.
 */

public class StarImageView extends android.support.v7.widget.AppCompatImageView {
  public StarImageView(Context context) {
    super(context);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
      setImageDrawable(context.getDrawable(R.drawable.difficulty_star));
    else
      setImageDrawable(context.getResources().getDrawable(R.drawable.difficulty_star));
  }

  public StarImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
      setImageDrawable(context.getDrawable(R.drawable.difficulty_star));
    else
      setImageDrawable(context.getResources().getDrawable(R.drawable.difficulty_star));
  }

  public StarImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
      setImageDrawable(context.getDrawable(R.drawable.difficulty_star));
    else
      setImageDrawable(context.getResources().getDrawable(R.drawable.difficulty_star));
  }
}
