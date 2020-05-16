package com.example.processapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

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
import android.widget.Toast;

import com.example.processapp.R;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttestationPresence#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttestationPresence extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText cin;
    private EditText nom;
    private EditText date;
    private EditText lieu_naiss;
    private EditText niveau;
    private EditText specialite;
    private EditText groupe;

    private Button button;

    private String token;


    public AttestationPresence() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttestationPresence.
     */
    // TODO: Rename and change types and number of parameters
    public static AttestationPresence newInstance(String param1, String param2) {
        AttestationPresence fragment = new AttestationPresence();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_attestation_presence, container, false);


        cin = view.findViewById(R.id.cin);
        date = view.findViewById(R.id.date);
        nom = view.findViewById(R.id.nom);
        lieu_naiss = view.findViewById(R.id.lieu);
        specialite = view.findViewById(R.id.specialite);
        groupe = view.findViewById(R.id.groupe);
        niveau = view.findViewById(R.id.niveau);

        cin.addTextChangedListener(loginTextWatcher);
        specialite.addTextChangedListener(loginTextWatcher);
        date.addTextChangedListener(loginTextWatcher);
        groupe.addTextChangedListener(loginTextWatcher);
        niveau.addTextChangedListener(loginTextWatcher);
        nom.addTextChangedListener(loginTextWatcher);
        lieu_naiss.addTextChangedListener(loginTextWatcher);


        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray.put(new JSONObject().put("CIN", cin.getText().toString()));
                    jsonArray.put(new JSONObject().put("niveau", niveau.getText().toString()));
                    jsonArray.put(new JSONObject().put("nom_etu", nom.getText().toString()));
                    jsonArray.put(new JSONObject().put("lieu", lieu_naiss.getText().toString()));
                    jsonArray.put(new JSONObject().put("spec", specialite.getText().toString()));
                    jsonArray.put(new JSONObject().put("groupe", groupe.getText().toString()));
                    jsonArray.put(new JSONObject().put("date_naissance", date.getText().toString()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("variables", jsonArray);
                    jsonObject.put("tas_uid", "8286862195c9e293bda63f7038987161");
                    jsonObject.put("pro_uid", "5982309495c9e28c3283ea0061483252");


                    Log.d("vvv", jsonObject.toString());

                    PresenceTask presenceTask = new PresenceTask();
                    presenceTask.execute(jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        return view;
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String cinInput = cin.getText().toString().trim();
            String specialiteInput = specialite.getText().toString().trim();
            String dateInput = date.getText().toString().trim();
            String nomInput = nom.getText().toString().trim();
            String lieuNaissInput = lieu_naiss.getText().toString().trim();
            String groupeInput = groupe.getText().toString().trim();
            String niveauGroupe = niveau.getText().toString().trim();


            button.setEnabled(!cinInput.isEmpty() && !specialiteInput.isEmpty() && !dateInput.isEmpty()&& !nomInput.isEmpty() && !lieuNaissInput.isEmpty() && !groupeInput.isEmpty() && !niveauGroupe.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public class PresenceTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://process.isiforge.tn/api/1.0/isi/cases");
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setConnectTimeout(6000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization","Bearer "+token);
                connection.setRequestProperty("Content-Type","application/json");
                connection.connect();
                try (OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream()) ) {

                    outputStream.write(params[0].getBytes());
                    outputStream.flush();
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {


                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line+"/n");
                        }
                        connection.getInputStream().close();
                        Log.d("response",stringBuffer.toString());

                        return stringBuffer.toString();
                    }

                } else {
                    Log.d("test2", String.valueOf(connection.getResponseMessage()));
                    return "Error in connexion";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_attestationPresence_to_nav_demande);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
        }
    }

}
