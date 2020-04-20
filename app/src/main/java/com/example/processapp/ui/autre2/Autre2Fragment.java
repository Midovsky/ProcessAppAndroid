package com.example.processapp.ui.autre2;

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

public class Autre2Fragment extends Fragment {

    private Autre2ViewModel autre2ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        autre2ViewModel =
                ViewModelProviders.of(this).get(Autre2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_autre2, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        autre2ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
