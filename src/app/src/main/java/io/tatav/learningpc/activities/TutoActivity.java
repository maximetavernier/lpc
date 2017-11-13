package io.tatav.learningpc.activities;

import io.tatav.learningpc.abs.activities.WebViewActivity;
import io.tatav.learningpc.entities.LearningTuto;

/**
 * Created by Tatav on 11/03/2017.
 */

public class TutoActivity extends WebViewActivity {
  /**
   * Data
   */
  private LearningTuto tuto;

  @Override
  protected void patchComponents() {
    super.patchComponents();

    tuto = (LearningTuto) getIntent().getSerializableExtra(LearningTuto.class.getSimpleName());
  }

  @Override
  protected void initComponents() {
    super.initComponents();
    super.load(tuto.getContentFile());
  }
}
