package io.tatav.learningpc.activities;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.tatav.learningpc.abs.activities.ContractActivity;
import io.tatav.learningpc.adapters.QuizzListAdapter;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.entitymanagers.QuizzManager;
import io.tatav.learningpc.utils.GsonConverter;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelComparator;
import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.utils.PairMap;
import io.tatav.learningpc.utils.Tags;
import rx.Observable;
import rx.Observer;
import rx.android.concurrency.AndroidSchedulers;
import rx.concurrency.Schedulers;
import rx.util.functions.Func1;

public final class QuizzListActivity extends ContractActivity implements Observer<List<LearningQuizz>> {
  /**
   * Data
   */
  private List<LearningQuizz> quizzes;
  private PairMap<Guid, LearningModelStatus> quizzesStatus;

  @Override
  protected void initComponents() {
    //if (quizzes == null || quizzes.isEmpty()) {
      subscription = Observable.from(this)
              .mapMany(new Func1<QuizzListActivity, Observable<List<LearningQuizz>>>() {
                @Override
                public Observable<List<LearningQuizz>> call(QuizzListActivity activity) {
                  return QuizzManager.getQuizzes(QuizzListActivity.this.getApplicationContext());
                }
              })
              .subscribeOn(Schedulers.threadPoolForIO())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(this);
    //}
  }

  @Override
  public void onCompleted() {
    if (quizzes != null && !quizzes.isEmpty()) {
      Collections.sort(quizzes, new LearningModelComparator());

      quizzesStatus = GsonConverter.getPairMapFromJsonArrayCallback(this.getPreferences(Tags.QUIZZ_PREF_TAG));
      if (quizzesStatus == null || quizzesStatus.isEmpty() || quizzesStatus.size() != quizzes.size()) {
        if (quizzesStatus == null)
          quizzesStatus = new PairMap<>(quizzes.size());
        for (LearningQuizz quizz : quizzes)
          if (!quizzesStatus.containsKey(quizz.getId()))
            quizzesStatus.put(quizz.getId(), LearningModelStatus.TODO);
        this.setPreferences(Tags.QUIZZ_PREF_TAG, GsonConverter.getJsonArrayFromPairMapCallback(quizzesStatus));
      }
      else
        for (LearningQuizz quizz : quizzes)
          quizz.setStatus(quizzesStatus.get(quizz.getId()));

      if (recyclerView.getAdapter() != null)
        recyclerView.getAdapter().notifyDataSetChanged();
      else
        recyclerView.setAdapter(new QuizzListAdapter(this, quizzes));
    }
  }

  @Override
  public void onError(Throwable e) {
    quizzes = null;
  }

  @Override
  public void onNext(List<LearningQuizz> args) {
    if (args != null) {
      if (quizzes == null)
        quizzes = args;
      else
        for (LearningQuizz quizz : args)
          if (!quizzes.contains(quizz))
            quizzes.add(quizz);
    }
  }
}
