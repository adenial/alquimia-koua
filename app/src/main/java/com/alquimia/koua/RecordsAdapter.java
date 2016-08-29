package com.alquimia.koua;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class RecordsAdapter extends RecyclerView.Adapter<RecordViewHolder>
{
  private List<Record> recordList;

  public RecordsAdapter(List<Record> recordList)
  {
    this.recordList = recordList;
  }

  @Override
  public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_list_row, parent, false);

    return new RecordViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(RecordViewHolder holder, int position)
  {
    final Record record = recordList.get(position);
    holder.title.setText(record.getTitle());
    holder.amount.setText(record.getAmount().toString());

    //somehow hardcoded.
    if (record.getOperationType() == 1)
    {
      // withdraw
      holder.operationType.setImageResource(R.drawable.creditcard);
    } else
    {
      // deposit
      holder.operationType.setImageResource(R.drawable.money);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        //
        Context context = v.getContext();
        Intent intent = new Intent(context, RecordDetailActivity.class);
        intent.putExtra(RecordDetailFragment.ARG_ITEM_ID, record.getRecordId());

        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount()
  {
    return recordList.size();
  }
}
