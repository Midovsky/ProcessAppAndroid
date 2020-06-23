package com.example.processapp.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.processapp.model.Task;
import com.example.processapp.ui.WkfFragment.OnListFragmentInteractionListener;


import java.util.List;
import com.example.processapp.R;
import com.example.processapp.ui.login.LoginActivity;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyWkfRecyclerViewAdapter extends RecyclerView.Adapter<MyWkfRecyclerViewAdapter.ViewHolder> {

    private  List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final WkfFragment mFragment;




    public MyWkfRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener, WkfFragment fragment) {
        mValues = items;
        mListener = listener;
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("adap","adap");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_wkf, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       // holder.mTasUidView.setText(mValues.get(position).getTas_uid());
        holder.mIdView.setText("Process Num "+String.valueOf(position+1));
        holder.mContentView.setText(mValues.get(position).getPro_title());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                    mListener.onListFragmentInteraction(holder.mItem);
                    // navController.navigate(R.id.action_nav_demande_to_reserveFragment2);


                }
            }
        });

        holder.mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onButtonClicked(holder.mContentView);


                    CharSequence text = holder.mContentView.getText();
                    if ("Demande de réservation de salle (Remplir Demande de réservation)".equals(text)) {
                        mFragment.displayFragmentSalle();

                    }
                    else if ("Demande de Double Correction (Remplir la demandede double correction)".equals(text)) {
                        mFragment.displayFragmentCorrection();
                    }
                    else if ("Demande attestation de présence (Remplir Demande d'attestation Présence)".equals(text)) {
                        mFragment.displayFragmentPresence();

                    }
                    else  {
                        Toast.makeText(holder.mView.getContext(), "pas d'action", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }



    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public void setmValues(List<Task> mValues) {
        this.mValues = mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
     //   public final TextView mTasUidView;
        public final Button mContentView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
        //    mTasUidView = (TextView) view.findViewById(R.id.tasuid);
            mContentView = (Button) view.findViewById(R.id.content);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
