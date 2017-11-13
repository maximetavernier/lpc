package io.tatav.learningpc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import io.tatav.learningpc.abs.fragments.PagerFragment;

/**
 * Created by Tatav on 23/02/2017.
 */

public final class SlidePagerAdapter extends FragmentStatePagerAdapter {
  private final List<PagerFragment> fragments;

  public SlidePagerAdapter(final FragmentManager fm, final List<PagerFragment> fragments) {
    super(fm);
    this.fragments = fragments;
  }

  @Override
  public final Fragment getItem(final int position) {
    return fragments.get(position);
  }

  @Override
  public final int getCount() {
    return fragments.size();
  }
}
