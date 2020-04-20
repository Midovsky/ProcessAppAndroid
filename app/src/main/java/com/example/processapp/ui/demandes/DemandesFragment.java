package com.example.processapp.ui.demandes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.processapp.R;

public class DemandesFragment extends Fragment {

    private DemandesViewModel demandesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        demandesViewModel =
                ViewModelProviders.of(this).get(DemandesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_demandes, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        demandesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
