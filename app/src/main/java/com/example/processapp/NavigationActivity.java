package com.example.processapp;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.Toast;

import com.example.processapp.model.Task;
import com.example.processapp.ui.ReserveFragment;
import com.example.processapp.ui.WkfFragment;
import com.example.processapp.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavigationActivity extends AppCompatActivity implements WkfFragment.OnListFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private String token;

     SharedPreferences sharedPref;
    private String mFName;

    WkfFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

/*        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState != null) {

            mFragment = (WkfFragment) fm.getFragment(savedInstanceState, "TABLE_FRAGMENT");
            mFName = savedInstanceState.getString("FNAMETAG");
        }*/

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, token, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations. (pour être considiérée dans le hamburger)
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_demande, R.id.nav_suivi, R.id.nav_autre1, R.id.nav_autre2)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Intent intent = getIntent();

        SharedPreferences result = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        token = result.getString("token", "no token found");





       /* navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();

                        // TODO: do stuff

                        int id = menuItem.getItemId();

                        if (id == R.id.nav_demande) {
                            Log.d("sss","sss");
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.nav_host_fragment, new WkfFragment())
                                    .commit();
                          //  ft.replace(R.id.nav_host_fragment, new WkfFragment()).commit();

                        }

                        if (id == R.id.nav_suivi) {

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .commit();
                        }

                        // You need this line to handle the navigation
                        boolean handled = NavigationUI.onNavDestinationSelected(menuItem, navController);
                        if (handled) {
                            ViewParent parent = navigationView.getParent();
                            if (parent instanceof DrawerLayout) {
                                ((DrawerLayout) parent).closeDrawer(navigationView);
                            }
                        }

                        return handled;
                    }
                });
*/

        //token= intent.getStringExtra("token");

    }



/*    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"TABLE_FRAGMENT",mFragment);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onListFragmentInteraction(Task item) {


    }

    @Override
    public void onButtonClicked(Button demande) {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        demande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (id == R.id.action_logout) {
            Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }




        return super.onOptionsItemSelected(item);
    }




}
