package com.example.whiskersapp.petwhiskers;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whiskersapp.petwhiskers.Model.Bookmark;
import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PetDetails extends AppCompatActivity {
    TextView petPrice, petBreed, petDesc;
    ImageView petImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    Toolbar toolbar;

    String id = "";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference table_pet_entry;
    private FirebaseAuth mAuth;

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
        table_pet_entry = firebaseDatabase.getReference("pet");
        mAuth = FirebaseAuth.getInstance();

        fab = (FloatingActionButton)findViewById(R.id.fav_fab);
        petImage =(ImageView)findViewById(R.id.img_pet);
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
                final String imageText = pet.getImgUrl();


                collapsingToolbarLayout.setTitle(pet.getPet_name());
                petBreed.setText(pet.getBreed());
                petDesc.setText(pet.getDetails());

                if (!imageText.equals("default_image")) {
                    Picasso.with(getBaseContext()).load(pet.getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.default_image).into(petImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(getBaseContext()).load(imageText).placeholder(R.drawable.default_image).into(petImage);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void bookmarkPet(View view){
        final String petId = getIntent().getStringExtra("id");
        final String user_id = mAuth.getCurrentUser().getUid();
        final Bookmark[] bookmark = {null};

        final DatabaseReference dbBookmark = firebaseDatabase.getReference("bookmark");

        dbBookmark.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Bookmark test = ds.getValue(Bookmark.class);

                    if(test.getId().equals(petId)){
                        bookmark[0] = test;
                    }
                }

                if(bookmark[0] == null){
                    //If bookmark does not exist
                    String id = dbBookmark.push().getKey();
                    Bookmark bookPet = new Bookmark();
                    Pet test = getPet(petId);

                    if(test != null){
                        bookPet.setId(id);
                        bookPet.setImgUrl(test.getImgUrl());
                        bookPet.setPet_name(test.getPet_name());
                        bookPet.setBreed(test.getBreed());
                        bookPet.setGender(test.getGender());
                        bookPet.setBookmark_user_id(user_id);
                        bookPet.setOwner_id(test.getOwner_id());

                        dbBookmark.child(id).setValue(bookPet);
                    }

                }else{

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Pet getPet(String id){
        DatabaseReference dbPetFind = firebaseDatabase.getReference("pet");
        final Pet[] pet = {null};

        dbPetFind.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    pet[0] = dataSnapshot.getValue(Pet.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return pet[0];
    }
}
