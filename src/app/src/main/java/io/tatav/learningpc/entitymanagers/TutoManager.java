package io.tatav.learningpc.entitymanagers;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.tatav.learningpc.entities.LearningTuto;
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
 * Created by Tatav on 11/03/2017.
 */

public final class TutoManager {
  private static final String TUTO_FILE_NAME = String.format("tutos_%s", ResourceManager.getLangage());

  private static final List<LearningTuto> getTutosFromRaw(Context context) {
    try {
      InputStream is = ResourceManager.getRawByName(context, TUTO_FILE_NAME);
      StringBuilder builder = new StringBuilder();
      int size = 0;
      while((size = is.available()) > 0) {
        byte[] buffer = new byte[size];
        is.read(buffer);
        builder.append(new String(buffer, "UTF-8"));
      }
      List l = GsonConverter.getListFromJsonArrayCallback(builder.toString(), LearningTuto.class);
      is.close();
      return l;
    } catch (IOException ex) {
      Log.e(Tags.APPLICATION_TAG, ex.getMessage());
      ex.printStackTrace();
    }
    return null;
  }

  private static LearningTuto getTutoFromRaw(Context context, final Guid id) {
    final List<LearningTuto> l = getTutosFromRaw(context);
    for (int i = 0; i < l.size(); ++i)
      if (l.get(i).getId().equals(id))
        return l.get(i);
    return null;
  }

  public static Observable<List<LearningTuto>> getTutos(final Context context) {
    return Observable.create(new Observable.OnSubscribeFunc<List<LearningTuto>>() {
      @Override
      public Subscription onSubscribe(final Observer<? super List<LearningTuto>> observer) {
        try {
          observer.onNext(getTutosFromRaw(context));
          observer.onCompleted();
        } catch (Exception e) {
          observer.onError(e);
        }
        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.threadPoolForIO());
  }

  public static Observable<LearningTuto> getTutoById(final Context context, final Guid id) {
    return Observable.create(new Observable.OnSubscribeFunc<LearningTuto>() {
      @Override
      public Subscription onSubscribe(final Observer<? super LearningTuto> observer) {
        try {
          observer.onNext(getTutoFromRaw(context, id));
          observer.onCompleted();
        } catch (Exception e) {
          observer.onError(e);
        }
        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.threadPoolForIO());
  }
}
