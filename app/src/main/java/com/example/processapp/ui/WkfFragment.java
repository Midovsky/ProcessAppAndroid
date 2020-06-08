package com.example.processapp.ui;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.processapp.R;
import com.example.processapp.TaskRepo;
import com.example.processapp.model.Task;


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
    static List<Task> lTask = new ArrayList<Task>() ;
    View myView = null;
    TaskRepo taskRepo;
    private String mFName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WkfFragment() {
    }

/*    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("FNAMETAG", mFName);
        super.onSaveInstanceState(outState);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         Log.d("oncreate",lTask.toString());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        SharedPreferences result = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        token = result.getString("token","no token found");

      //  Log.d("task",lTask.toString());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (myView == null) {
            View view = inflater.inflate(R.layout.fragment_wkf_list, container, false);

            Log.d("nono",lTask.toString());

            myView = view;
            return myView;

        }

        else{
            return myView;

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int i=0;
        Log.d("task",lTask.toString());


        // Set the adapter
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setHasFixedSize(true);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }




            if (lTask.isEmpty()){


                Log.d("task",String.valueOf(i++));

                taskRepo = new TaskRepo(getActivity());
                taskRepo.possibleTasks();
                lTask = taskRepo.getlTask();

                Log.d("dafuq1",lTask.toString());


              /*  lTask.add(new Task("tas_uid","pro_title","pro_uid"));
                lTask.add(new Task("tas_uid","pro_title","pro_uid"));
                lTask.add(new Task("tas_uid","pro_title","pro_uid"));*/

            }

           //textView.setText(lTask.get(0).getPro_title());


        Log.d("dafuq2",lTask.toString());

        MyWkfRecyclerViewAdapter myWkfRecyclerViewAdapter = new MyWkfRecyclerViewAdapter(lTask,mListener, this);
        recyclerView.setAdapter(myWkfRecyclerViewAdapter);

        Log.d("count",String.valueOf(myWkfRecyclerViewAdapter.getItemCount()));

        // String s = (String) lTask.get(0).getPro_title();

        // myWkfRecyclerViewAdapter.notifyDataSetChanged();


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

    public void displayFragmentSalle(){
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_demande_to_reserveFragment2);

    }

    public void displayFragmentPresence() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_demande_to_attestationPresence);

    }

    public void displayFragmentCorrection() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_demande_to_doubleCorrection);

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
        void onButtonClicked(Button demande);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("resume","resume");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("destroy","destroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("pause","pause");

    }
}


