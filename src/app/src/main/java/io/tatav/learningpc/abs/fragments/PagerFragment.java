package io.tatav.learningpc.abs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.tatav.learningpc.abs.activities.ViewPagerActivity;
import io.tatav.learningpc.abs.entities.LearningModel;

/**
 * Created by Tatav on 23/02/2017.
 */

public abstract class PagerFragment extends Fragment {
  protected ViewPagerActivity context;
  protected RelativeLayout layout;

  protected LearningModel model;
  protected int index;

  public PagerFragment() {
    this(null);
  }

  public PagerFragment(LearningModel model) {
    this.model = model;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      model = (LearningModel) getArguments().getSerializable("model");
      index = getArguments().getInt("index");
    }
  }

  public static <T extends PagerFragment> T newInstance(Class<T> c, int index, LearningModel model) {
    T fragment = null;

    try {
      fragment = c.newInstance();
      Bundle args = new Bundle();
      args.putSerializable("model", model);
      args.putInt("index", index);
      fragment.setArguments(args);
    } catch (java.lang.InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    context = (ViewPagerActivity) getActivity();
    initUI();
  }

  public void initUI() {
    patchComponents();
    initComponents();
  }

  /**
   * Abstract patch component function
   */
  protected abstract void patchComponents();
  protected abstract void initComponents();
}
