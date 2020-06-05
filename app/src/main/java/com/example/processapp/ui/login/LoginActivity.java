package com.example.processapp.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.processapp.NavigationActivity;
import com.example.processapp.R;

public class LoginActivity extends AppCompatActivity {


    ProgressBar loadingProgressBar;
    SharedPreferences sharedPref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText usernameEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
         loadingProgressBar = findViewById(R.id.loading);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("grant_type","password");
                    jsonObject.put("username",usernameEditText.getText().toString());
                    jsonObject.put("password",passwordEditText.getText().toString());
                    jsonObject.put("scope","*");
                    jsonObject.put("client_id","WMZNSSETCJDPTZSVETRNOPGYFKMAKHHQ");
                    jsonObject.put("client_secret","5813427175e8e5d18452a90035077331");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                UserLoginTask userLoginTask = new UserLoginTask();
                userLoginTask.execute(jsonObject.toString());
            }
        });
    }


    public class UserLoginTask extends AsyncTask<String, Integer, String>  {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loadingProgressBar.setVisibility(View.VISIBLE);

            loadingProgressBar.setProgress(0);

            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Connexion en cours");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            loadingProgressBar.setProgress(0);

            loadingProgressBar.setVisibility(View.INVISIBLE);

            try {
                JSONObject j = new JSONObject(s);

                String res = j.getString("access_token");
                Log.e("wiou",res);


                if (res != ""){
                    Toast.makeText(LoginActivity.this, "welcome", Toast.LENGTH_LONG).show();


                    sharedPref = getSharedPreferences("myPref",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token",res);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class) ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("token", res);
                    getApplicationContext().startActivity(intent);
                }
                else  {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
            loadingProgressBar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e("sss",params[0]);
                URL url = new URL("http://process.isiforge.tn/isi/oauth2/token");
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setConnectTimeout(6000);
                connection.setRequestMethod("POST");
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
                        publishProgress(10);
                        Log.e("responselogin",stringBuffer.toString());
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



    }


}
