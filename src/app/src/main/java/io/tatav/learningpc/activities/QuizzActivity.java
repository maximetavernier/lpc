package io.tatav.learningpc.activities;

import java.util.ArrayList;
import java.util.List;

import io.tatav.learningpc.abs.activities.ViewPagerActivity;
import io.tatav.learningpc.abs.fragments.PagerFragment;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.fragments.QuizzBorderFragment;
import io.tatav.learningpc.fragments.QuizzPartFragment;
import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.utils.Tags;

public final class QuizzActivity extends ViewPagerActivity {
  /**
   * Data
   */
  private LearningQuizz quizz;

  @Override
  protected void patchComponents() {
    super.patchComponents();

    model = quizz = (LearningQuizz) getIntent().getSerializableExtra(LearningQuizz.class.getSimpleName());
  }

  @Override
  protected void initComponents() {
    super.initComponents();

    if (quizz.getStatus().equals(LearningModelStatus.TODO)) {
      quizz.setStatus(LearningModelStatus.DOING);

      updateStatus(Tags.QUIZZ_PREF_TAG, quizz.getId(), LearningModelStatus.DOING);
    }
  }

  @Override
  protected List<PagerFragment> initFragments() {
    if (model != null && model instanceof LearningQuizz)
      quizz = (LearningQuizz) model;
    List<PagerFragment> fragments = new ArrayList<>(quizz.getParts().size() + 2);
    final int size = quizz.getParts() != null ? quizz.getParts().size() : 0;

    for (int i = 0; i < size + 2; ++i)
      fragments.add(i, PagerFragment.newInstance(i == 0 || i == size + 1 ? QuizzBorderFragment.class : QuizzPartFragment.class, i, quizz));

    return fragments;
  }
}
