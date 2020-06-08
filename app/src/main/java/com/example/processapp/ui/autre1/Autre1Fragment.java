package com.example.processapp.ui.autre1;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.processapp.R;

public class Autre1Fragment extends Fragment {

    private Autre1ViewModel autre1ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        autre1ViewModel =
                ViewModelProviders.of(this).get(Autre1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_autre1, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        autre1ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}
