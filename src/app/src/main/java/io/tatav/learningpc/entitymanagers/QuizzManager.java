package io.tatav.learningpc.entitymanagers;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.utils.Tags;
import io.tatav.learningpc.utils.GsonConverter;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.ResourceManager;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Tatav on 24/02/2017.
 */
public final class QuizzManager {
  private static final String QUIZZ_FILE_NAME = String.format("quizzes_%s", ResourceManager.getLangage());

  private static final List<LearningQuizz> getQuizzesFromRaw(Context context) {
    try {
      InputStream is = ResourceManager.getRawByName(context, QUIZZ_FILE_NAME);
      StringBuilder builder = new StringBuilder();
      int size = 0;
      while((size = is.available()) > 0) {
        byte[] buffer = new byte[size];
        is.read(buffer);
        builder.append(new String(buffer, "UTF-8"));
      }
      List l = GsonConverter.getListFromJsonArrayCallback(builder.toString(), LearningQuizz.class);
      is.close();
      return l;
    } catch (IOException ex) {
      Log.e(Tags.APPLICATION_TAG, ex.getMessage());
      ex.printStackTrace();
    }
    return null;
  }

  public static LearningQuizz getQuizzFromRaw(final Context context, final Guid id) {
    final List<LearningQuizz> l = getQuizzesFromRaw(context);
    for (int i = 0; i < l.size(); ++i)
      if (l.get(i).getId().equals(id))
        return l.get(i);
    return null;
  }

  public static Observable<List<LearningQuizz>> getQuizzes(final Context context) {
    return Observable.create(new Observable.OnSubscribeFunc<List<LearningQuizz>>() {
      @Override
      public Subscription onSubscribe(final Observer<? super List<LearningQuizz>> observer) {
        try {
          observer.onNext(getQuizzesFromRaw(context));
          observer.onCompleted();
        } catch (Exception e) {
          observer.onError(e);
        }
        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.threadPoolForIO());
  }

  public static Observable<LearningQuizz> getQuizzById(final Context context, final Guid id) {
    return Observable.create(new Observable.OnSubscribeFunc<LearningQuizz>() {
      @Override
      public Subscription onSubscribe(final Observer<? super LearningQuizz> observer) {
        try {
          observer.onNext(getQuizzFromRaw(context, id));
          observer.onCompleted();
        } catch (Exception e) {
          observer.onError(e);
        }
        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.threadPoolForIO());
  }
}
