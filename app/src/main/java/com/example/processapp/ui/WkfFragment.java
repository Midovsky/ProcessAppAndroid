package com.example.processapp.ui;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.processapp.R;
import com.example.processapp.model.Task;
import com.example.processapp.model.Wkf;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WkfFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private String token;
    public List<Task> lTask = new ArrayList<Task>() ;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WkfFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WkfFragment newInstance(int columnCount) {
        WkfFragment fragment = new WkfFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        SharedPreferences result = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        token = result.getString("token","no token found");

        Log.d("task",lTask.toString());

    }

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wkf_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
           // recyclerView.setAdapter(new MyWkfRecyclerViewAdapter(DummyContent.ITEMS, mListener));

    
            SearchProcessTask searchProcessTask = new SearchProcessTask();
            searchProcessTask.execute();
            MyWkfRecyclerViewAdapter myWkfRecyclerViewAdapter = new MyWkfRecyclerViewAdapter(lTask,mListener);
            recyclerView.setAdapter(myWkfRecyclerViewAdapter);
            myWkfRecyclerViewAdapter.notifyDataSetChanged();
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Task item);
        void onButtonClicked(Button demande); // You may want to edit the arguments of the method
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
                        Log.e("response",stringBuffer.toString());
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
}


