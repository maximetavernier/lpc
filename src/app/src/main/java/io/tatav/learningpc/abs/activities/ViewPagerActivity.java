package io.tatav.learningpc.abs.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;
import java.util.Map;

import io.tatav.learningpc.R;
import io.tatav.learningpc.abs.entities.LearningModel;
import io.tatav.learningpc.abs.fragments.PagerFragment;
import io.tatav.learningpc.adapters.SlidePagerAdapter;
import io.tatav.learningpc.animations.DepthPageTransformer;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.entities.QuizzPart;
import io.tatav.learningpc.fragments.QuizzBorderFragment;
import io.tatav.learningpc.fragments.QuizzPartFragment;
import io.tatav.learningpc.utils.GsonConverter;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.utils.PairMap;
import io.tatav.learningpc.utils.Tags;
import io.tatav.learningpc.views.UnscrollableViewPager;

import static android.R.attr.mode;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Tatav on 23/02/2017.
 */

public abstract class ViewPagerActivity extends FragmentActivity implements SharedPreferencesActivity, StatusUpdater {
  /**
   * Preferences
   */
  private static SharedPreferences preferences;

  /**
   * Data
   */
  protected List<PagerFragment> fragments;
  protected int page = -1;
  protected Bundle savedBundle;
  protected LearningModel model;

  /**
   * Components
   */
  // The unscrollableViewPager widget, that handles animation and allows swiping horizontally to access button_previous and button_next wizard steps.
  private UnscrollableViewPager unscrollableViewPager;
  private ImageButton previousImageButton;
  private ImageButton nextImageButton;

  // The unscrollableViewPager adapter, that provides the pages to the view unscrollableViewPager widget.
  private PagerAdapter pagerAdapter;

  protected abstract List<PagerFragment> initFragments();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pager);
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    initUI();
  }

  protected void initUI() {
    patchComponents();
    initComponents();
  }

  protected void patchComponents() {
    unscrollableViewPager = (UnscrollableViewPager) findViewById(R.id.unscrollableViewPager);
    previousImageButton = (ImageButton) findViewById(R.id.previousImageButton);
    nextImageButton = (ImageButton) findViewById(R.id.nextImageButton);
  }

  protected void initComponents() {
    if (savedBundle == null)
      savedBundle = new Bundle();
    else {
      model = (LearningModel) savedBundle.getSerializable("model");
      page = savedBundle.getInt("page");
    }

    pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), fragments = initFragments());
    unscrollableViewPager.setAdapter(pagerAdapter);
    unscrollableViewPager.setPageTransformer(true, new DepthPageTransformer());
    unscrollableViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(final int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          previousImageButton.setImageDrawable(getDrawable(position != 0 ? R.drawable.button_enabled_previous : R.drawable.button_back));
          nextImageButton.setImageDrawable(getDrawable(position != fragments.size() - 1 ? R.drawable.button_enabled_next : R.drawable.button_success));
        } else {
          previousImageButton.setImageDrawable(getResources().getDrawable(position != 0 ? R.drawable.button_enabled_previous : R.drawable.button_back));
          nextImageButton.setImageDrawable(getResources().getDrawable(position != fragments.size() - 1 ? R.drawable.button_enabled_next : R.drawable.button_success));
        }
        page = position;

        if (page > 0 && page < fragments.size() - 1) {
          QuizzPartFragment qpf = (QuizzPartFragment) fragments.get(page);
          nextImageButton.setEnabled(qpf.isRadioGroupItemSelected());
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            nextImageButton.setImageDrawable(getDrawable(qpf.isRadioGroupItemSelected() ? R.drawable.button_enabled_next : R.drawable.button_disabled_next));
          else
            nextImageButton.setImageDrawable(getResources().getDrawable(qpf.isRadioGroupItemSelected() ? R.drawable.button_enabled_next : R.drawable.button_disabled_next));
        }
        else {
          if (page == fragments.size() - 1)
            fragments.get(page).initUI();
          nextImageButton.setEnabled(true);
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });

    nextImageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (unscrollableViewPager.getCurrentItem() == fragments.size() - 1) {
          PagerFragment fragment = fragments.get(fragments.size() - 1);
          if (fragment instanceof QuizzBorderFragment) {
            // If note = max, then quizz is done
            if (((QuizzBorderFragment) fragment).getNote().equals(((LearningQuizz) model).getParts().size())) {
              updateStatus(Tags.QUIZZ_PREF_TAG, model.getId(), LearningModelStatus.DONE);
            }
          }
          finish();
        }
        else
          unscrollableViewPager.setCurrentItem(unscrollableViewPager.getCurrentItem() + 1);
      }
    });

    previousImageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (unscrollableViewPager.getCurrentItem() != 0)
          unscrollableViewPager.setCurrentItem(unscrollableViewPager.getCurrentItem() - 1);
        else
          finish();
      }
    });

    if (page < 0 && fragments.size() > 0)
      page = 0;
    if (fragments.size() > page)
      unscrollableViewPager.setCurrentItem(page);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      previousImageButton.setImageDrawable(getApplicationContext().getDrawable(page != 0 ? R.drawable.button_enabled_previous : R.drawable.button_back));
      nextImageButton.setImageDrawable(getApplicationContext().getDrawable(page != fragments.size() - 1 ? R.drawable.button_enabled_next : R.drawable.button_success));
    } else {
      previousImageButton.setImageDrawable(getResources().getDrawable(page != 0 ? R.drawable.button_enabled_previous : R.drawable.button_back));
      nextImageButton.setImageDrawable(getResources().getDrawable(page != fragments.size() - 1 ? R.drawable.button_enabled_next : R.drawable.button_success));
    }
  }

  @Override
  public void onBackPressed() {
    if (unscrollableViewPager.getCurrentItem() > 0)
      unscrollableViewPager.setCurrentItem(unscrollableViewPager.getCurrentItem() - 1);
    else
      finish();
  }

  public ImageButton getPreviousImageButton() {
    return previousImageButton;
  }

  public ImageButton getNextImageButton() {
    return nextImageButton;
  }

  @Override
  public boolean setPreferences(String key, String value) {
    if (preferences == null)
      preferences = getSharedPreferences(Tags.APPLICATION_TAG, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    return editor.putString(key, value).commit();
  }

  @Override
  public String getPreferences(String key) {
    if (preferences == null)
      preferences = getSharedPreferences(Tags.APPLICATION_TAG, Context.MODE_PRIVATE);
    return preferences.getString(key, null);
  }

  @Override
  public void updateStatus(String tag, Guid entityId, LearningModelStatus status) {
    PairMap<Guid, LearningModelStatus> statusMap = GsonConverter.getPairMapFromJsonArrayCallback(getPreferences(tag));
    statusMap.update(entityId, status);
    setPreferences(tag, GsonConverter.getJsonArrayFromPairMapCallback(statusMap));
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
      onSaveInstanceState(savedBundle);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    savedBundle.putInt("page", unscrollableViewPager.getCurrentItem());
    savedBundle.putSerializable("model", model);
    outState.putBundle("savedBundle", savedBundle);

    initUI();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    savedBundle = savedInstanceState.getBundle("savedBundle");
    page = savedBundle.getInt("page");
    model = (LearningModel) savedBundle.getSerializable("model");
  }
}
