package io.tatav.learningpc.abs.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.util.Map;

import io.tatav.learningpc.R;
import io.tatav.learningpc.activities.LessonActivity;
import io.tatav.learningpc.utils.GsonConverter;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.utils.PairMap;
import io.tatav.learningpc.utils.ResourceManager;
import io.tatav.learningpc.utils.Tags;

import static android.R.attr.scrollY;

/**
 * Created by Tatav on 11/03/2017.
 */

public abstract class WebViewActivity extends AppCompatActivity implements SharedPreferencesActivity, StatusUpdater {
  /**
   * Preferences
   */
  private static SharedPreferences preferences;

  /**
   * Data
   */
  protected Bundle savedBundle;

  /**
   * Components
   */
  protected WebView webView;
  protected ImageButton previousImageButton;
  protected ImageButton gotoQuizzImageButton;
  protected ImageButton finishImageButton;

  /**
   * Abstract functions
   */
  protected void patchComponents() {
    webView = (WebView) findViewById(R.id.webView);
    previousImageButton = (ImageButton) findViewById(R.id.previousImageButton);
    gotoQuizzImageButton = (ImageButton) findViewById(R.id.gotoQuizzImageButton);
    finishImageButton = (ImageButton) findViewById(R.id.finishImageButton);
  }

  protected void initComponents() {
    if (savedBundle == null)
      savedBundle = new Bundle();
    if (this instanceof LessonActivity) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
          @Override
          public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            double webViewSize = Math.floor(webView.getContentHeight() * webView.getScale());
            gotoQuizzImageButton.setVisibility((scrollY + webView.getMeasuredHeight() >= webViewSize) ? View.VISIBLE : View.GONE);
            finishImageButton.setVisibility((scrollY + webView.getMeasuredHeight() >= webViewSize) ? View.VISIBLE : View.GONE);
          }
        });
        double webViewSize = Math.floor(webView.getContentHeight() * webView.getScale());

        gotoQuizzImageButton.setVisibility(webView.getMeasuredHeight() >= webViewSize ? View.VISIBLE : View.GONE);
        finishImageButton.setVisibility(webView.getMeasuredHeight() >= webViewSize ? View.VISIBLE : View.GONE);
      } else {
        gotoQuizzImageButton.setVisibility(View.VISIBLE);
        finishImageButton.setVisibility(View.VISIBLE);
      }
    }
    previousImageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  /**
   * Overrided functions
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.webview_activity);
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    initUI();
  }

  private void initUI() {
    patchComponents();
    initComponents();
  }

  protected void load(String name) {
    webView.loadUrl(String.format("%s/%s/%s", "file:///android_asset/www", ResourceManager.getLangage(), name));
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

    initUI();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }
}
