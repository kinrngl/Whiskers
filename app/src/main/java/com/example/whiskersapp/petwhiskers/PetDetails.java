package com.example.whiskersapp.petwhiskers;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

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
        DatabaseReference dbBookmark = FirebaseDatabase.getInstance().getReference("bookmark");
        final String petId = getIntent().getStringExtra("id");

        if(petId != null){
            dbBookmark.orderByChild("pet_id").equalTo(petId);
            dbBookmark.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Bookmark temp = null;

                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Bookmark test = ds.getValue(Bookmark.class);

                        if(test.getPet_id().equals(petId)){
                            temp=test;
                        }
                    }

                    if(temp == null){
                        addBookmark(petId);
                        Toast.makeText(getApplicationContext(), "Bookmark Added!", Toast.LENGTH_SHORT).show();
                    }else{
                        removeBookmark(temp.getId());
                        Toast.makeText(getApplicationContext(), "Bookmark Removed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error in DB!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "ID not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addBookmark(final String petId){
        final FirebaseAuth userAuth = FirebaseAuth.getInstance();
        DatabaseReference dbPet = FirebaseDatabase.getInstance().getReference("pet");
        final DatabaseReference dbBookmark = FirebaseDatabase.getInstance().getReference("bookmark");

        dbPet.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pet pet = null;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Pet test = ds.getValue(Pet.class);

                    if(test.getId().equals(petId)){
                        pet = test;
                    }
                }

                if(pet != null){
                    Bookmark bookmark = new Bookmark();
                    String id = dbBookmark.push().getKey();

                    bookmark.setId(id);
                    bookmark.setPet_name(pet.getPet_name());
                    bookmark.setBreed(pet.getBreed());
                    bookmark.setGender(pet.getGender());
                    bookmark.setOwner_id(pet.getOwner_id());
                    bookmark.setBookmark_user_id(userAuth.getCurrentUser().getUid());
                    bookmark.setImgUrl(pet.getImgUrl());
                    bookmark.setPet_id(pet.getId());
                    bookmark.setStatus(pet.getStatus());

                    dbBookmark.child(id).setValue(bookmark);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeBookmark(String bookmarkId){
        DatabaseReference dbBookmark = FirebaseDatabase.getInstance().getReference("bookmark");

        dbBookmark.child(bookmarkId).removeValue();
    }

    public void messageOwner(View view){
        String pet_owner_id = getIntent().getStringExtra("owner_id");
        Intent intent = new Intent(PetDetails.this, ChatActivity.class);

        intent.putExtra("user_one_id", mAuth.getCurrentUser().getUid());
        intent.putExtra("user_two_id", pet_owner_id);

        startActivity(intent);
    }

}
