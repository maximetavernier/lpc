package io.tatav.learningpc.abs.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import io.tatav.learningpc.R;
import io.tatav.learningpc.utils.Tags;
import rx.Subscription;

/**
 * Created by Tatav on 18/03/2017.
 */

public abstract class ContractActivity extends AppCompatActivity implements SharedPreferencesActivity {
  /**
   * Preferences
   */
  private static SharedPreferences preferences;

  /**
   * Bundle
   */
  protected Bundle savedBundle;

  /**
   * Components
   */
  protected RecyclerView recyclerView;
  protected RecyclerView.LayoutManager layoutManager;

  /**
   * Data
   */
  protected Subscription subscription;

  /**
   * Abstract Methods
   */
  protected abstract void initComponents();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contract);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    try
    {
      if (subscription != null)
        subscription.unsubscribe();
    } catch(Exception ex) {
      Log.e(Tags.APPLICATION_TAG, ex.getMessage());
    } finally {
      subscription = null;
    }

    try
    {
      if (savedBundle != null)
        savedBundle.clear();
    } catch(Exception ex) {
      Log.e(Tags.APPLICATION_TAG, ex.getMessage());
    } finally {
      savedBundle = null;
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

    initUI();
  }

  @Override
  public void onBackPressed() {
    finish();
  }

  protected void initUI() {
    if (savedBundle == null)
      savedBundle = new Bundle();
    patchComponents();
    initComponents();
  }

  protected void patchComponents() {
    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
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
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
      onSaveInstanceState(savedBundle);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBundle("savedBundle", savedBundle);
    initUI();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    savedBundle = savedInstanceState.getBundle("savedBundle");
  }
}
