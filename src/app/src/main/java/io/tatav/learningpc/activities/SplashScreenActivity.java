package io.tatav.learningpc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import io.tatav.learningpc.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public final class SplashScreenActivity extends AppCompatActivity {
  /**
   * Components
   */
  private ImageView logoImageView;

  /**
   * Animation
   */
  private Animation animation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_splash_screen);
  }

  @Override
  protected void onStart() {
    super.onStart();

    initUI();
    hide();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    animateLogo();
  }

  private void hide() {
    // Hide UI first
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.hide();
    }
  }

  protected void initUI() {
    patchComponents();
    initComponents();
  }

  protected void patchComponents() {
    logoImageView = (ImageView) findViewById(R.id.logoImageView);
  }

  protected void initComponents() {
    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }
    });
  }

  private void animateLogo() {
    logoImageView.startAnimation(animation);
  }
}
