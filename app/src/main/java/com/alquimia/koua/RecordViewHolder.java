package com.alquimia.koua;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecordViewHolder extends RecyclerView.ViewHolder
{
  public TextView title;
  public TextView amount;
  public ImageView operationType;

  public RecordViewHolder(View view)
  {
    super(view);
    title = (TextView) view.findViewById(R.id.title);
    amount = (TextView) view.findViewById(R.id.amount);
    operationType = (ImageView) view.findViewById(R.id.imageView);
  }
}
