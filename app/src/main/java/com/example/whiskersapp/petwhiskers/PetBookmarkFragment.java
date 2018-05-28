package com.example.whiskersapp.petwhiskers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.example.whiskersapp.petwhiskers.ViewHolder.PetBookmarkViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PetBookmarkFragment extends Fragment {
    /*private RecyclerView recyclerview;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;
    private List<Pet> petList;
    private FirebaseAuth mAuth;
    private PetBookmarkViewHolder bookmarkAdapter;*/

    public PetBookmarkFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet_bookmark, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*recyclerview = view.findViewById(R.id.BookMarkPetRV);
        petList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("pet");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ownerId = mAuth.getCurrentUser().getUid();
                Pet test;

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    test = ds.getValue(Pet.class);
                    if(!test.getOwner_id().equals(ownerId)){
                        petList.add(test);
                    }
                }

                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(llm);

                bookmarkAdapter = new PetBookmarkViewHolder(getContext(), petList);
                recyclerview.setAdapter(bookmarkAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error in retrieving data!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
