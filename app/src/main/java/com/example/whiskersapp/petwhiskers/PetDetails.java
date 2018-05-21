package com.example.whiskersapp.petwhiskers;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PetDetails extends AppCompatActivity {
    TextView petPrice, petBreed, petDesc;
    //ImageView petImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    Toolbar toolbar;

    String id = "";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference table_pet_entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_pet);
        toolbar.setNavigationIcon(R.drawable.ic_back_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        table_pet_entry = firebaseDatabase.getReference("pet_entry");

        fab = (FloatingActionButton)findViewById(R.id.fav_fab);
        petPrice = (TextView)findViewById(R.id.pet_price_details);
        petBreed = (TextView)findViewById(R.id.pet_breed);
        petDesc = (TextView)findViewById(R.id.pet_description_details);
        //petImage = (ImageView)findViewById(R.id.img_pet);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_pet);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if(getIntent()!=null){
            id = getIntent().getStringExtra("id");
            if(!id.isEmpty()){
                getPetDetails(id);
            }
        }
    }

    private void getPetDetails(String id) {
        table_pet_entry.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pet pet = dataSnapshot.getValue(Pet.class);

                collapsingToolbarLayout.setTitle(pet.getPetBreed());
                petPrice.setText(pet.getPetPrice());
                petBreed.setText(pet.getPetBreed());
                petDesc.setText(pet.getPetDesc());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
