package com.alquimia.koua;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alquimia.koua.repository.RecordRepository;

/**
 * A fragment representing a single Record detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link RecordDetailActivity}
 * on handsets.
 */
public class RecordDetailFragment extends Fragment
{
  private RecordRepository recordRepository = new RecordRepository();

  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";

  /**
   * The dummy content this fragment is presenting.
   */
  private Record mItem;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecordDetailFragment()
  {
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(ARG_ITEM_ID))
    {
      long recordId = getArguments().getLong(ARG_ITEM_ID);

      mItem = recordRepository.getRecordById(getContext(), recordId);

      Activity activity = this.getActivity();
      CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
      if (appBarLayout != null)
      {
        appBarLayout.setTitle(mItem.getTitle());
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.record_detail, container, false);

    // Show the dummy content as text in a TextView.
    if (mItem != null)
    {
      TextView amount = (TextView) rootView.findViewById(R.id.lblamount);
      amount.setText(amount.getText() + "  $" + mItem.getAmount().toString());
      TextView date = (TextView) rootView.findViewById(R.id.lbldate);
      date.setText(date.getText() + "   " + mItem.getDate());
      TextView smsMessage = (TextView) rootView.findViewById(R.id.lblmessage);
      smsMessage.setText(smsMessage.getText() + "   " + mItem.getSmsMessage());
    }

    return rootView;
  }
}
