package io.tatav.learningpc.activities;

import java.util.Collections;
import java.util.List;

import io.tatav.learningpc.abs.activities.ContractActivity;
import io.tatav.learningpc.adapters.TutoListAdapter;
import io.tatav.learningpc.entities.LearningLesson;
import io.tatav.learningpc.entities.LearningTuto;
import io.tatav.learningpc.entitymanagers.TutoManager;
import io.tatav.learningpc.utils.LearningModelComparator;
import rx.Observable;
import rx.Observer;
import rx.android.concurrency.AndroidSchedulers;
import rx.concurrency.Schedulers;
import rx.util.functions.Func1;

/**
 * Created by Tatav on 11/03/2017.
 */

public final class TutoListActivity extends ContractActivity implements Observer<List<LearningTuto>> {
  /**
   * Data
   */
  private List<LearningTuto> tutos;

  @Override
  protected void initComponents() {
    //if (tutos == null || tutos.isEmpty()) {
      subscription = Observable.from(this)
              .mapMany(new Func1<TutoListActivity, Observable<List<LearningTuto>>>() {
                @Override
                public Observable<List<LearningTuto>> call(TutoListActivity activity) {
                  return TutoManager.getTutos(activity.getApplicationContext());
                }
              })
              .subscribeOn(Schedulers.threadPoolForIO())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(this);
    //}
  }



  @Override
  public void onCompleted() {
    if (tutos != null && !tutos.isEmpty()) {
      Collections.sort(tutos, new LearningModelComparator());
      if (recyclerView.getAdapter() != null)
        recyclerView.getAdapter().notifyDataSetChanged();
      else
        recyclerView.setAdapter(new TutoListAdapter(this, tutos));
    }
  }

  @Override
  public void onError(Throwable e) {
    tutos = null;
  }

  @Override
  public void onNext(List<LearningTuto> args) {
    if (args != null) {
      if (tutos == null)
        tutos = args;
      else
        for (LearningTuto tuto : args)
          if (!tutos.contains(tuto))
            tutos.add(tuto);
    }
  }
}
