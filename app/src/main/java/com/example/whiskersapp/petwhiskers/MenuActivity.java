package com.example.whiskersapp.petwhiskers;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whiskersapp.petwhiskers.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference table_user;
    private User user, utest;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(MenuActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }else{
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            firebaseDatabase = FirebaseDatabase.getInstance();
            table_user = firebaseDatabase.getReference("user_account");

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot children: dataSnapshot.getChildren()){
                        utest = children.getValue(User.class);
                        if(utest.getEmail().equals(firebaseAuth.getCurrentUser().getEmail())){
                            user = utest;
                            Toast.makeText(getApplicationContext(), "Welcome "+user.getFname(), Toast.LENGTH_LONG).show();
                            ((TextView)findViewById(R.id.navHeader_name)).setText(user.getFname() +" "+user.getLname());
                    }
                }
            }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            progressDialog.dismiss();
        }

        FloatingActionButton petEntryFAB = findViewById(R.id.cpefab);

        petEntryFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentPetEntry = new PetEntryFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentFrame, fragmentPetEntry);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("");


            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem home = menu.findItem(R.id.nav_home);
        home.setTitle("Home");

        MenuItem findPet = menu.findItem(R.id.nav_findpet);
        findPet.setTitle("Find Pet");

        MenuItem petEntry = menu.findItem(R.id.nav_petentry);
        petEntry.setTitle("Pet");

        MenuItem message = menu.findItem(R.id.nav_message);
        message.setTitle("Message");

        MenuItem account = menu.findItem(R.id.nav_account);
        account.setTitle("Account");

        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.contentFrame, new HomeFragment());
        tx.commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        ProgressDialog progressDialog = new ProgressDialog(MenuActivity.this);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            toolbar.setTitle("Home");
        } else if (id == R.id.nav_findpet) {
            fragment = new FindPetFragment();
            toolbar.setTitle("Find Pet");
        } else if (id == R.id.nav_petentry) {
            fragment = new PetFragment();
            toolbar.setTitle("Pet");
        } else if (id == R.id.nav_message) {
            fragment = new MessageFragment();
            toolbar.setTitle("Message");
        } else if (id == R.id.nav_account) {
            fragment = new AccountDisplayFragment();
            toolbar.setTitle("Account");
        } else if (id == R.id.nav_logout) {
            firebaseAuth = FirebaseAuth.getInstance();
            progressDialog.setMessage("Logging out...");
            progressDialog.show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, StartActivity.class));

        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

