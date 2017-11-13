package io.tatav.learningpc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.tatav.learningpc.R;
import io.tatav.learningpc.abs.fragments.PagerFragment;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.entities.QuizzPart;
import io.tatav.learningpc.utils.Tags;

/**
 * Created by Tatav on 23/02/2017.
 */

public final class QuizzBorderFragment extends PagerFragment {
  /**
   * Components
   */
  private TextView quizzBorderTitleTextView;
  private TextView quizzBorderContentTextView;

  /**
   * Data
   */
  private String content;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return (layout = (RelativeLayout)inflater.inflate(R.layout.fragment_quizz_border, container, false));
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (index != 0)
      Log.i(Tags.APPLICATION_TAG, "Hello !");
  }

  @Override
  protected void patchComponents() {
    quizzBorderTitleTextView = (TextView) layout.findViewById(R.id.quizzBorderTitleTextView);
    quizzBorderContentTextView = (TextView) layout.findViewById(R.id.quizzBorderContentTextView);
  }

  @Override
  protected void initComponents() {
    LearningQuizz quizz = (LearningQuizz) model;

    quizzBorderTitleTextView.setText(quizz.getTitle());
    if (index == 0)
      updateContent(quizz.getIntroduction());
    else {
      final int note = getNote();
      final int max = quizz.getParts().size();
      updateContent(quizz.getConclusion(), note, max, getStringEvaluation(note, max));
    }
  }

  public final void updateContent(String content) {
    quizzBorderContentTextView.setText(content);
  }

  public final void updateContent(String content, int goodAnswer, int maxAnswer, String evaluation) {
    quizzBorderContentTextView.setText(String.format(content, goodAnswer, maxAnswer, evaluation));
  }

  public final Integer getNote() {
    int value = 0;

    for (QuizzPart part : ((LearningQuizz) model).getParts()) {
      value += part.getGoodAnswer() == part.getUserAnswer() ? 1 : 0;
    }
    return value;
  }

  private final String getStringEvaluation(int note, int max) {
    return getResources().getStringArray(R.array.quizz_result_appreciation)[note == max ? 3 : note >= (max * 70 / 100) ? 2 : note >= (max * 30 /100) ? 1 : 0];
  }
}
