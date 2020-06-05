package com.example.processapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.processapp.R;
import com.example.processapp.TaskRepo;

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

public class DoubleCorrection extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText cin;
    private  EditText nomEtudiant;
    private  EditText email;
    private EditText groupe;
    private  EditText specialite;
    private  EditText matiere;
    private  Spinner typeEpreuve;
    private  Spinner session;
    private  EditText enseignant;
    private  EditText niveau;
    private  EditText commentaire;
    private Button button;

    private String token;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoubleCorrection() {
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
        View view = inflater.inflate(R.layout.fragment_double_correction, container, false);


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        button = (Button) view.findViewById(R.id.button);

        cin = view.findViewById(R.id.cin);
        niveau = view.findViewById(R.id.niveau);
        nomEtudiant = view.findViewById(R.id.nom);
        specialite = view.findViewById(R.id.specialite);
        email = view.findViewById(R.id.email);
        groupe = view.findViewById(R.id.groupe);
        matiere = view.findViewById(R.id.matiere);
        typeEpreuve = (Spinner) view.findViewById(R.id.typeEpreuve);
        session = (Spinner) view.findViewById(R.id.session);
        enseignant = view.findViewById(R.id.enseignant);
        commentaire = view.findViewById(R.id.commentaire);


        cin.addTextChangedListener(loginTextWatcher);
        specialite.addTextChangedListener(loginTextWatcher);
        matiere.addTextChangedListener(loginTextWatcher);
        groupe.addTextChangedListener(loginTextWatcher);
        niveau.addTextChangedListener(loginTextWatcher);
        enseignant.addTextChangedListener(loginTextWatcher);
        email.addTextChangedListener(loginTextWatcher);
        nomEtudiant.addTextChangedListener(loginTextWatcher);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONArray jsonArray = new JSONArray();
                try {

                    jsonArray.put(new JSONObject().put("cin", cin.getText().toString()));
                    jsonArray.put(new JSONObject().put("niveau", niveau.getText().toString()));
                    jsonArray.put(new JSONObject().put("nom_etudiant", String.valueOf(nomEtudiant.getText().toString())));
                    jsonArray.put(new JSONObject().put("specialite", String.valueOf(specialite.getText().toString())));
                    jsonArray.put(new JSONObject().put("mail", email.getText().toString()));
                    jsonArray.put(new JSONObject().put("groupe", groupe.getText())).toString();
                    jsonArray.put(new JSONObject().put("matiere", matiere.getText().toString()));
                    jsonArray.put(new JSONObject().put("type_epreuve",typeEpreuve.getSelectedItem()));
                    jsonArray.put(new JSONObject().put("Session", session.getSelectedItem()));
                    jsonArray.put(new JSONObject().put("enseignant", enseignant.getText()).toString());
                    jsonArray.put(new JSONObject().put("commentaire", commentaire.getText().toString()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("variables", jsonArray);
                    jsonObject.put("tas_uid", "4203867315e8f9e98e40123052633099");
                    jsonObject.put("pro_uid", "8925311585e8f9e98a07556058383142");


                    Log.d("vvv", jsonObject.toString());

                    TaskRepo taskRepo = new TaskRepo(getActivity());

                    taskRepo.doubleCorrection(jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String cinInput = cin.getText().toString().trim();
            String specialiteInput = specialite.getText().toString().trim();
            String emailInput = email.getText().toString().trim();
            String nomInput = nomEtudiant.getText().toString().trim();
            String niveauInput = niveau.getText().toString().trim();
            String groupeInput = groupe.getText().toString().trim();
            String matiereInput = matiere.getText().toString().trim();
            String enseignantInput = enseignant.getText().toString().trim();


            button.setEnabled(!cinInput.isEmpty() && !specialiteInput.isEmpty() && !specialiteInput.isEmpty()&& !nomInput.isEmpty() && !emailInput.isEmpty() && !groupeInput.isEmpty() && !niveauInput.isEmpty()  && !matiereInput.isEmpty()  && !enseignantInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };




}
