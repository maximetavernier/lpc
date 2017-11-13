package io.tatav.learningpc.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.tatav.learningpc.R;
import io.tatav.learningpc.abs.fragments.PagerFragment;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.entities.QuizzPart;

/**
 * Created by Tatav on 23/02/2017.
 */

public final class QuizzPartFragment extends PagerFragment {
  /**
   * Components
   */
  private TextView quizzPartTitleTextView;
  private TextView quizzPartQuestionTextView;
  private RadioGroup quizzPartChoiceRadioGroup;

  /**
   * Data
   */
  private QuizzPart quizzPart;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return (layout = (RelativeLayout)inflater.inflate(R.layout.fragment_quizz_part, container, false));
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  protected void patchComponents() {
    quizzPartTitleTextView = (TextView) layout.findViewById(R.id.quizzPartTitleTextView);
    quizzPartQuestionTextView = (TextView) layout.findViewById(R.id.quizzPartQuestionTextView);
    quizzPartChoiceRadioGroup = (RadioGroup) layout.findViewById(R.id.quizzPartChoiceRadioGroup);
  }

  @Override
  protected void initComponents() {
    quizzPart = ((LearningQuizz) model).getParts().get(index - 1);

    quizzPartTitleTextView.setText(model.getTitle());
    quizzPartQuestionTextView.setText(quizzPart.getQuestion());
    for (int i = 0; i < quizzPart.getChoices().size(); ++i) {
      RadioButton rdbtn = new RadioButton(getActivity());
      rdbtn.setId(quizzPartChoiceRadioGroup.getId() + i + 1);
      rdbtn.setText(quizzPart.getChoices().get(i));
      quizzPartChoiceRadioGroup.addView(rdbtn);
    }

    quizzPartChoiceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(final RadioGroup group, @IdRes final int checkedId) {
        context.getNextImageButton().setEnabled(checkedId != -1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
          context.getNextImageButton().setImageDrawable(getActivity().getDrawable(checkedId != -1 ? R.drawable.button_enabled_next : R.drawable.button_disabled_next));
        else
          context.getNextImageButton().setImageDrawable(getActivity().getResources().getDrawable(checkedId != -1 ? R.drawable.button_enabled_next : R.drawable.button_disabled_next));
        if (checkedId != -1)
          quizzPart.setUserAnswer(quizzPartChoiceRadioGroup.getCheckedRadioButtonId());
      }
    });
  }

  public final boolean isRadioGroupItemSelected() {
    return quizzPartChoiceRadioGroup.getCheckedRadioButtonId() != -1;
  }

  public final int getRadioGroupSelectedItem() {
    return quizzPartChoiceRadioGroup.indexOfChild(quizzPartChoiceRadioGroup.findViewById(quizzPartChoiceRadioGroup.getCheckedRadioButtonId()));
  }
}
