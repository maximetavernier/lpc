package io.tatav.learningpc.activities;

import android.content.Intent;
import android.view.View;

import io.tatav.learningpc.R;
import io.tatav.learningpc.abs.activities.WebViewActivity;
import io.tatav.learningpc.entities.LearningLesson;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.entitymanagers.QuizzManager;
import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.utils.Tags;

public final class LessonActivity extends WebViewActivity implements View.OnClickListener {
  /**
   * Data
   */
  private LearningLesson lesson;

  @Override
  protected void patchComponents() {
    super.patchComponents();

    lesson = (LearningLesson) getIntent().getSerializableExtra(LearningLesson.class.getSimpleName());
  }

  @Override
  protected void initComponents() {
    super.initComponents();
    super.load(lesson.getContentFile());
    if (lesson.getStatus().equals(LearningModelStatus.TODO)) {
      lesson.setStatus(LearningModelStatus.DOING);

      updateStatus(Tags.LESSON_PREF_TAG, lesson.getId(), LearningModelStatus.DOING);
    }
    finishImageButton.setOnClickListener(this);
    gotoQuizzImageButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.finishImageButton:
        updateStatus(Tags.LESSON_PREF_TAG, lesson.getId(), LearningModelStatus.DONE);
        break;
      case R.id.gotoQuizzImageButton:
        startActivity(new Intent(getApplicationContext(), QuizzActivity.class).putExtra(LearningQuizz.class.getSimpleName(), QuizzManager.getQuizzFromRaw(this, lesson.getQuizzId())));
        break;
    }
    finish();
  }
}
