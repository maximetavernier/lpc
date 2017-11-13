package io.tatav.learningpc.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.tatav.learningpc.R;
import io.tatav.learningpc.activities.TutoActivity;
import io.tatav.learningpc.activities.TutoListActivity;
import io.tatav.learningpc.entities.LearningTuto;
import io.tatav.learningpc.utils.ResourceManager;
import io.tatav.learningpc.views.StarImageView;


/**
 * Created by Tatav on 11/03/2017.
 */

public class TutoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String DEFAULT_PICTURE = "drawable/default_tuto";

  private final TutoListActivity context;
  private final List<LearningTuto> list;

  public TutoListAdapter(final TutoListActivity context, final List<LearningTuto> list) {
    this.context = context;
    this.list = list;
  }

  @Override
  public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
    return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tuto_item, parent, false)) { };
  }

  @Override
  public final void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    final LearningTuto item = list.get(position);

    final LinearLayout tutoItemDifficultyLayout = (LinearLayout) holder.itemView.findViewById(R.id.tutoItemDifficultyLayout);
    final ImageView tutoItemPictureImageView = (ImageView) holder.itemView.findViewById(R.id.tutoItemPictureImageView);
    final TextView tutoItemTitle = (TextView) holder.itemView.findViewById(R.id.tutoItemTitle);
    final TextView tutoItemDescription = (TextView) holder.itemView.findViewById(R.id.tutoItemDescription);

    tutoItemTitle.setText(item.getTitle());
    tutoItemDescription.setText(item.getDescription());
    tutoItemPictureImageView.setImageDrawable(ResourceManager.getDrawableByName(context, item.getPicture(), DEFAULT_PICTURE));
    if (tutoItemDifficultyLayout.getChildCount() < item.getDifficulty())
      for (int i = 0; i < item.getDifficulty() - tutoItemDifficultyLayout.getChildCount(); ++i)
        tutoItemDifficultyLayout.addView(new StarImageView(context));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public final void onClick(final View v) {
        context.startActivity(new Intent(context.getApplicationContext(), TutoActivity.class).putExtra(LearningTuto.class.getSimpleName(), list.get(position)));
      }
    });
  }

  @Override
  public final int getItemCount() {
    return list.size();
  }
}
