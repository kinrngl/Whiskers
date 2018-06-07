package com.example.whiskersapp.petwhiskers;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

<<<<<<< HEAD
import android.support.v7.widget.SearchView;
=======
import android.util.Log;
>>>>>>> dev-eevee
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
<<<<<<< HEAD

    private FloatingActionButton petEntryFAB;

=======
    private FloatingActionButton petEntryFAB;
>>>>>>> dev-eevee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        petEntryFAB = findViewById(R.id.cpefab);
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

<<<<<<< HEAD
        petEntryFAB = findViewById(R.id.cpefab);
=======
>>>>>>> dev-eevee
        petEntryFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentPetEntry = new PetEntryFragment();
<<<<<<< HEAD
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentFrame, fragmentPetEntry);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("");
                petEntryFAB.hide();
=======
                if(fragmentPetEntry != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentFrame, fragmentPetEntry);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Create Pet Entry");
                    petEntryFAB.hide();
                }

>>>>>>> dev-eevee
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

        MenuItem map = menu.findItem(R.id.nav_map);
        map.setTitle("Map");

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
        petEntryFAB.show();
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
            petEntryFAB.show();
            toolbar.setTitle("Home");
            petEntryFAB.show();
        } else if (id == R.id.nav_findpet) {
            fragment = new FindPetFragment();
            petEntryFAB.show();
            toolbar.setTitle("Find Pet");
            petEntryFAB.show();
        }else if (id == R.id.nav_map) {
            fragment = new MapFragment();
            petEntryFAB.show();
            toolbar.setTitle("Map");
        } else if (id == R.id.nav_petentry) {
            fragment = new PetFragment();
            petEntryFAB.show();
            toolbar.setTitle("Pet");
            petEntryFAB.show();
        } else if (id == R.id.nav_message) {
            fragment = new MessageFragment();
            petEntryFAB.hide();
            toolbar.setTitle("Message");
            petEntryFAB.hide();
        } else if (id == R.id.nav_account) {
            fragment = new AccountDisplayFragment();
            petEntryFAB.hide();
            toolbar.setTitle("Account");
            petEntryFAB.hide();
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
<<<<<<< HEAD
=======
            petEntryFAB.show();
>>>>>>> dev-eevee
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("check activity","Test1");
        if ((requestCode == AddLocationFragment.REQUEST_CHECK_SETTINGS) || (requestCode == MapFragment.REQUEST_CHECK_SETTINGS)){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

