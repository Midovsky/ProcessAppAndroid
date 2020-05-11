package com.example.processapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.processapp.NavigationActivity;
import com.example.processapp.R;
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


        Button button = (Button) view.findViewById(R.id.button);

        RadioGroup rdGrp = view.findViewById(R.id.rdGrp);
        RadioButton rdOui = view.findViewById(R.id.rdOui);
        RadioButton rdNon = view.findViewById(R.id.rdNon);

        EditText cin = view.findViewById(R.id.cin);
        EditText resp = view.findViewById(R.id.resp);
        EditText date = view.findViewById(R.id.date);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = rdGrp.getCheckedRadioButtonId();
                RadioButton rdb = getActivity().findViewById(radioId);
                Toast.makeText(getContext(), rdb.getText(),
                        Toast.LENGTH_SHORT).show();
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
                    jsonObject.put("tas_uid", "8286862195c9e293bda63f7038987161");
                    jsonObject.put("pro_uid", "5982309495c9e28c3283ea0061483252");


                    Log.d("vvv", jsonObject.toString());

                    ReserveClassTask reserveClassTask = new ReserveClassTask();
                    reserveClassTask.execute(jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;

    }

    public class ReserveClassTask extends AsyncTask<String, Integer, String> {


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


            try {
                JSONObject j = new JSONObject(s);

                String res = j.getString("access_token");
                Log.e("wiou",res);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
        }
    }
}
