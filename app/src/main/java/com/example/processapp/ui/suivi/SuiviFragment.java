package com.example.processapp.ui.suivi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.processapp.R;
import com.example.processapp.TaskRepo;
import com.example.processapp.model.Case;
import com.example.processapp.ui.ParticipatedCasesAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuiviFragment extends Fragment {

    private SuiviViewModel suiviViewModel;
    private List<Case> cases = new ArrayList<Case>();
    private String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences result = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        token = result.getString("token","no token found");

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        suiviViewModel =
                ViewModelProviders.of(this).get(SuiviViewModel.class);
        View root = inflater.inflate(R.layout.fragment_suivi, container, false);

      /*  suiviViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

             Context context = view.getContext();
             RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            //cases.clear();

        if (cases.isEmpty()){
            TaskRepo taskRepo = new TaskRepo(getActivity());
            taskRepo.participatedCases();
            cases = taskRepo.getCases();

        }

        ParticipatedCasesAdapter adapter = new ParticipatedCasesAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setCases(cases);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
