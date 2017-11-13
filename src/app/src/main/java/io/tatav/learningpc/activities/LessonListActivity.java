package io.tatav.learningpc.activities;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.tatav.learningpc.abs.activities.ContractActivity;
import io.tatav.learningpc.adapters.LessonListAdapter;
import io.tatav.learningpc.entities.LearningLesson;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.entitymanagers.LessonManager;
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

public final class LessonListActivity extends ContractActivity implements Observer<List<LearningLesson>> {
  /**
   * Data
   */
  private List<LearningLesson> lessons;
  private PairMap<Guid, LearningModelStatus> lessonsStatus;

  @Override
  protected void initComponents() {
    //if (lessons == null || lessons.isEmpty()) {
      subscription = Observable.from(this)
              .mapMany(new Func1<LessonListActivity, Observable<List<LearningLesson>>>() {
                @Override
                public Observable<List<LearningLesson>> call(LessonListActivity activity) {
                  return LessonManager.getLessons(activity.getApplicationContext());
                }
              })
              .subscribeOn(Schedulers.threadPoolForIO())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(this);
    //}
  }

  @Override
  public void onCompleted() {
    if (lessons != null && !lessons.isEmpty()) {
      Collections.sort(lessons, new LearningModelComparator());

      lessonsStatus = GsonConverter.getPairMapFromJsonArrayCallback(this.getPreferences(Tags.LESSON_PREF_TAG));
      if (lessonsStatus == null || lessonsStatus.isEmpty() || lessonsStatus.size() != lessons.size()) {
        if (lessonsStatus == null)
          lessonsStatus = new PairMap<>(lessons.size());
        for (LearningLesson lesson : lessons)
          if (!lessonsStatus.containsKey(lesson.getId()))
            lessonsStatus.put(lesson.getId(), LearningModelStatus.TODO);
        this.setPreferences(Tags.LESSON_PREF_TAG, GsonConverter.getJsonArrayFromPairMapCallback(lessonsStatus));
      }
      else
        for (LearningLesson lesson : lessons)
          lesson.setStatus(lessonsStatus.get(lesson.getId()));

      if (recyclerView.getAdapter() != null)
        recyclerView.getAdapter().notifyDataSetChanged();
      else
        recyclerView.setAdapter(new LessonListAdapter(this, lessons));
    }
  }

  @Override
  public void onError(Throwable e) {
    lessons = null;
  }

  @Override
  public void onNext(List<LearningLesson> args) {
    if (args != null) {
      if (lessons == null)
        lessons = args;
      else
        for (LearningLesson lesson : args)
          if (!lessons.contains(lesson))
            lessons.add(lesson);
    }
  }
}
