package com.example.whiskersapp.petwhiskers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< Updated upstream


public class HomeFragment extends Fragment {
=======
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private Pet pet;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbPet;

    private ImageView imgPet;
    private TextView petname, petbreed, petstatus;

>>>>>>> Stashed changes
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
<<<<<<< Updated upstream
=======
        pet = null;

        petname = view.findViewById(R.id.pet_featurename);
        petbreed = view.findViewById(R.id.pet_featurebreed);
        petstatus = view.findViewById(R.id.pet_featuretrans);
        imgPet = view.findViewById(R.id.pet_featureimg);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbPet = firebaseDatabase.getReference("pet");

        dbPet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Pet test = ds.getValue(Pet.class);

                    if(i==0){
                        pet = test;
                        i++;
                    }
                }

                if(pet != null){
                    petname.setText(pet.getPet_name());
                    petbreed.setText(pet.getBreed());
                    petstatus.setText(pet.getStatus());
                    Glide.with(getContext()).load(pet.getImgUrl()).into(imgPet);
                    Toast.makeText(getContext(), "Value Retrieved!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
>>>>>>> Stashed changes
    }

}
