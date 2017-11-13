package io.tatav.learningpc.adapters;

import android.content.Intent;
import android.os.Build;
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
import io.tatav.learningpc.activities.QuizzActivity;
import io.tatav.learningpc.activities.QuizzListActivity;
import io.tatav.learningpc.utils.Guid;
import io.tatav.learningpc.utils.LearningModelStatus;
import io.tatav.learningpc.entities.LearningQuizz;
import io.tatav.learningpc.utils.ResourceManager;
import io.tatav.learningpc.views.StarImageView;
import io.tatav.learningpc.views.StatusDependent;

import static io.tatav.learningpc.R.id.quizzItemDifficultyLayout;
import static io.tatav.learningpc.R.id.quizzItemStatusTextView;

/**
 * Created by Tatav on 15/02/2017.
 */
public final class QuizzListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StatusDependent {
  private static final String DEFAULT_PICTURE = "drawable/default_quizz";

  private final QuizzListActivity context;
  private final List<LearningQuizz> list;

  public QuizzListAdapter(final QuizzListActivity context, final List<LearningQuizz> list) {
    this.context = context;
    this.list = list;
  }

  @Override
  public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
    return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_quizz_item, parent, false)) { };
  }

  @Override
  public final void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    final LearningQuizz item = list.get(position);

    final LinearLayout quizzItemDifficultyLayout = (LinearLayout) holder.itemView.findViewById(R.id.quizzItemDifficultyLayout);
    final ImageView quizzItemPictureImageView = (ImageView) holder.itemView.findViewById(R.id.quizzItemPictureImageView);
    final TextView quizzItemTitle = (TextView) holder.itemView.findViewById(R.id.quizzItemTitle);
    final TextView quizzItemDescription = (TextView) holder.itemView.findViewById(R.id.quizzItemDescription);
    final TextView quizzItemStatusTextView = (TextView) holder.itemView.findViewById(R.id.quizzItemStatusTextView);

    quizzItemTitle.setText(item.getTitle());
    quizzItemDescription.setText(item.getDescription());
    quizzItemPictureImageView.setImageDrawable(ResourceManager.getDrawableByName(context, item.getPicture(), DEFAULT_PICTURE));
    if (quizzItemDifficultyLayout.getChildCount() < item.getDifficulty())
      for (int i = 0; i < item.getDifficulty() - quizzItemDifficultyLayout.getChildCount(); ++i)
        quizzItemDifficultyLayout.addView(new StarImageView(context));
    quizzItemStatusTextView.setText(getTextByStatus(item.getStatus()));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public final void onClick(final View v) {
        context.startActivity(new Intent(context.getApplicationContext(), QuizzActivity.class).putExtra(LearningQuizz.class.getSimpleName(), list.get(position)));
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
