package com.example.processapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.processapp.R;
import com.example.processapp.model.Case;

import java.util.List;

public class ParticipatedCasesAdapter extends RecyclerView.Adapter<ParticipatedCasesAdapter.CaseHolder> {

    private  List<Case> cases;



    @NonNull
    @Override
    public CaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("adap2","adap");

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participated_case, parent, false);
        return new CaseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipatedCasesAdapter.CaseHolder holder, int position) {

        Case currentCase = cases.get(position);
        holder.mIdName.setText(cases.get(position).getName());
        holder.mIdState.setText(cases.get(position).getState());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return cases.size();
    }
    public void setCases(List<Case> cases) {
        this.cases = cases;
        notifyDataSetChanged();
    }


    public class CaseHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView mIdName;
        public  TextView mIdState;

        public CaseHolder(View view) {
            super(view);
            mView = view;
            mIdName = (TextView) view.findViewById(R.id.case_name);
            mIdState = (TextView) view.findViewById(R.id.case_state);

        }


    }
}
