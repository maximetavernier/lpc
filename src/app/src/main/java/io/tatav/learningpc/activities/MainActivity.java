package io.tatav.learningpc.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import io.tatav.learningpc.R;
import io.tatav.learningpc.abs.activities.SharedPreferencesActivity;
import io.tatav.learningpc.utils.Tags;

public final class MainActivity extends AppCompatActivity implements View.OnClickListener {
  /**
   * Bundle
   */
  protected Bundle savedBundle;

  /**
   * Components
   */
  private LinearLayout mainLayout;

  private Button tutoImageButton;
  private Button lessonImageButton;
  private Button quizzImageButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  protected void onStart() {
    super.onStart();

    initUI();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
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

  protected void initUI() {
    if (savedBundle == null)
      savedBundle = new Bundle();
    patchComponents();
    initComponents();
  }

  protected void patchComponents() {
    mainLayout = (LinearLayout) findViewById(R.id.mainLayout);

    tutoImageButton = (Button) mainLayout.findViewById(R.id.tutoButton);
    lessonImageButton = (Button) mainLayout.findViewById(R.id.lessonButton);
    quizzImageButton = (Button) mainLayout.findViewById(R.id.quizzButton);
  }

  protected void initComponents() {
    tutoImageButton.setOnClickListener(this);
    lessonImageButton.setOnClickListener(this);
    quizzImageButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tutoButton:
        startActivity(new Intent(getApplicationContext(), TutoListActivity.class));
        break;
      case R.id.lessonButton:
        startActivity(new Intent(getApplicationContext(), LessonListActivity.class));
        break;
      case R.id.quizzButton:
        startActivity(new Intent(getApplicationContext(), QuizzListActivity.class));
        break;
      default:
        return;
    }
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
