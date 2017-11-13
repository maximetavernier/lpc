package io.tatav.learningpc.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.tatav.learningpc.R;
import io.tatav.learningpc.activities.LessonActivity;
import io.tatav.learningpc.activities.LessonListActivity;
import io.tatav.learningpc.entities.LearningLesson;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.utils.ResourceManager;
import io.tatav.learningpc.views.StarImageView;
import io.tatav.learningpc.views.StatusDependent;

/**
 * Created by Tatav on 15/02/2017.
 */

public final class LessonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StatusDependent {
  private static final String DEFAULT_PICTURE = "drawable/default_lesson";

  private final LessonListActivity context;
  private final List<LearningLesson> list;

  public LessonListAdapter(final LessonListActivity context, final List<LearningLesson> list) {
    this.context = context;
    this.list = list;
  }

  @Override
  public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
    return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lesson_item, parent, false)) { };
  }

  @Override
  public final void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    final LearningLesson item = list.get(position);

    final LinearLayout lessonItemDifficultyLayout = (LinearLayout) holder.itemView.findViewById(R.id.lessonItemDifficultyLayout);
    final ImageView lessonItemPictureImageView = (ImageView) holder.itemView.findViewById(R.id.lessonItemPictureImageView);
    final TextView lessonItemTitle = (TextView) holder.itemView.findViewById(R.id.lessonItemTitle);
    final TextView lessonItemDescription = (TextView) holder.itemView.findViewById(R.id.lessonItemDescription);
    final TextView lessonItemStatusTextView = (TextView) holder.itemView.findViewById(R.id.lessonItemStatusTextView);

    lessonItemTitle.setText(item.getTitle());
    lessonItemDescription.setText(item.getDescription());
    lessonItemPictureImageView.setImageDrawable(ResourceManager.getDrawableByName(context, item.getPicture(), DEFAULT_PICTURE));
    if (lessonItemDifficultyLayout.getChildCount() < item.getDifficulty())
      for (int i = 0; i < item.getDifficulty() - lessonItemDifficultyLayout.getChildCount(); ++i)
        lessonItemDifficultyLayout.addView(new StarImageView(context));
    lessonItemStatusTextView.setText(getTextByStatus(item.getStatus()));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public final void onClick(final View v) {
        context.startActivity(new Intent(context.getApplicationContext(), LessonActivity.class).putExtra(LearningLesson.class.getSimpleName(), list.get(position)));
      }
    });
  }

  @Override
  public final int getItemCount() {
    return list.size();
  }

  @Override
  public String getTextByStatus(LearningModelStatus status) {
    return context.getResources().getStringArray(R.array.learning_model_status)[status.value()];
  }
}
