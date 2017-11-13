package io.tatav.learningpc.entities;

import java.util.ArrayList;

import io.tatav.learningpc.abs.entities.LearningPartModel;

/**
 * Created by Tatav on 23/02/2017.
 */

public final class QuizzPart extends LearningPartModel {
  private int questionId;
  private String question;
  private ArrayList<String> choices;
  private int userAnswer = -1;
  private int goodAnswer;

  public int getGoodAnswer() {
    return goodAnswer;
  }

  public void setGoodAnswer(int goodAnswer) {
    this.goodAnswer = goodAnswer;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public ArrayList<String> getChoices() {
    return choices;
  }

  public void setChoices(ArrayList<String> choices) {
    this.choices = choices;
  }

  public int getUserAnswer() {
    return userAnswer;
  }

  public void setUserAnswer(int userAnswer) {
    this.userAnswer = userAnswer;
  }

  private boolean isGoodAnswer() {
    return userAnswer == goodAnswer;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }
}
