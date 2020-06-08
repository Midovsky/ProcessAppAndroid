package com.example.processapp;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.processapp.model.Case;
import com.example.processapp.model.Task;

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


public class TaskRepo {
    private String token;
    private  Activity activity;

    public List<Task> lTask = new ArrayList<Task>();

    private List<Case> cases = new ArrayList<Case>();



    public List<Task> getlTask() {

        return lTask;
    }

    public List<Case> getCases() {
        return cases;
    }

    public TaskRepo(Activity activity) {
        SharedPreferences result = activity.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        token = result.getString("token","no token found");
        this.activity = activity;
    }

    public void reserveSalle(String data) {

        ReserveSalleTask reserveSalleTask = new ReserveSalleTask();
        reserveSalleTask.execute(data);
    }

    public void possibleTasks() {

        SearchProcessTask searchProcessTask = new SearchProcessTask();
        searchProcessTask.execute();
    }

    public void doubleCorrection(String data) {

        DoubleCorrectionTask doubleCorrectionTask = new DoubleCorrectionTask();
       doubleCorrectionTask.execute(data);
    }

    public void attestationPresence(String data) {

        PresenceTask presenceTask = new PresenceTask();
        presenceTask.execute(data);
    }

    public void participatedCases() {

        ParticipatedTasks participatedTasks = new ParticipatedTasks();
        participatedTasks.execute();
    }


    public class ReserveSalleTask extends AsyncTask<String, Integer, String> {


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
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();
                try (OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream())) {

                    outputStream.write(params[0].getBytes());
                    outputStream.flush();
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {


                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line + "/n");
                        }
                        connection.getInputStream().close();
                        Log.d("response", stringBuffer.toString());

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
            navController.navigate(R.id.action_reserveFragment2_to_nav_demande);
        }

    }

    public class SearchProcessTask extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {

                URL url = new URL("http://process.isiforge.tn/api/1.0/isi/case/start-cases");
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization","Bearer "+token);
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line+"/n");
                        }
                        connection.getInputStream().close();
                        Log.d("ttt",stringBuffer.toString());
                        return stringBuffer.toString();
                    }

                } else {
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("test1",s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String tas_uid = obj.getString("tas_uid");
                    String pro_title = obj.getString("pro_title");
                    String pro_uid = obj.getString("pro_uid");

                    Task task = new Task(tas_uid,pro_title,pro_uid);

                    lTask.add(task);
                }
                Log.d("test3",lTask.toString());



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public class DoubleCorrectionTask extends AsyncTask<String, Integer, String> {


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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
            navController.navigate(R.id.action_doubleCorrection_to_nav_demande);

        }

    }

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
            navController.navigate(R.id.action_attestationPresence_to_nav_demande);
        }


    }

    public class ParticipatedTasks extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL("http://process.isiforge.tn/api/1.0/isi/cases/participated?limit=10");
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization","Bearer "+token);
                connection.connect();

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("test99",s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String caseName = obj.getString("app_pro_title");
                    String caseState = obj.getString("app_status_label");

                    Case aCase = new Case(caseName,caseState);

                    cases.add(aCase);
                }
                Log.d("testCases",cases.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }



}