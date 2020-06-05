package com.example.processapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.processapp.NavigationActivity;
import com.example.processapp.R;
import com.example.processapp.TaskRepo;
import com.example.processapp.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReserveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  EditText cin;
    private  EditText resp;
    private  EditText date;
    private  Button button;


    private String token;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReserveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        SharedPreferences result = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        token = result.getString("token","no token found");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve, container, false);

        // Spinner element
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        Spinner raisonSpinner = (Spinner) view.findViewById(R.id.raisonSpinner);


         button = (Button) view.findViewById(R.id.button);

        RadioGroup rdGrp = view.findViewById(R.id.rdGrp);
        RadioButton rdOui = view.findViewById(R.id.rdOui);
        RadioButton rdNon = view.findViewById(R.id.rdNon);

         cin = view.findViewById(R.id.cin);
         resp = view.findViewById(R.id.resp);
         date = view.findViewById(R.id.date);

        cin.addTextChangedListener(loginTextWatcher);
        resp.addTextChangedListener(loginTextWatcher);
        date.addTextChangedListener(loginTextWatcher);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = rdGrp.getCheckedRadioButtonId();
                RadioButton rdb = getActivity().findViewById(radioId);

                Log.d("vb", "vb");


                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray.put(new JSONObject().put("CIN", cin.getText().toString()));
                    jsonArray.put(new JSONObject().put("nom_res", resp.getText().toString()));
                    jsonArray.put(new JSONObject().put("Club", String.valueOf(spinner.getSelectedItem())));
                    jsonArray.put(new JSONObject().put("raison", String.valueOf(raisonSpinner.getSelectedItem())));
                    jsonArray.put(new JSONObject().put("date", date.getText().toString()));
                    jsonArray.put(new JSONObject().put("videoprojecteur", rdb.getText()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("raison", String.valueOf(raisonSpinner.getSelectedItem()));
                    jsonObject.put("date", date.getText().toString());
                    jsonObject.put("videoprojecteur", rdb.getText());
                    jsonObject.put("variables", jsonArray);
                    jsonObject.put("tas_uid", "6771638215cab32556ced52048389332");
                    jsonObject.put("pro_uid", "3471224625ca4eb91dc51d0089637545");


                    Log.d("vvv", jsonObject.toString());

                   TaskRepo taskRepo = new TaskRepo(getActivity());

                    taskRepo.reserveSalle(jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String cinInput = cin.getText().toString().trim();
            String respInput = resp.getText().toString().trim();
            String dateInput = date.getText().toString().trim();

            button.setEnabled(!cinInput.isEmpty() && !respInput.isEmpty() && !dateInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };



}
