package com.example.whiskersapp.petwhiskers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.example.whiskersapp.petwhiskers.ViewHolder.FindPetViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FindPetFragment extends Fragment {
    private RecyclerView recyclerview;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference table_pet_entry;
    private FirebaseRecyclerAdapter<Pet, FindPetViewHolder> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_pet, container, false);
        recyclerview = (RecyclerView)view.findViewById(R.id.findPetRV);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseDatabase = FirebaseDatabase.getInstance();
        table_pet_entry = firebaseDatabase.getReference("pet_entry");
        adapter = new FirebaseRecyclerAdapter<Pet, FindPetViewHolder>(
                Pet.class,
                R.layout.find_pet_item,
                FindPetViewHolder.class,
                table_pet_entry.orderByChild("petCategory").equalTo("Dog")
        ) {
            @Override
            protected void populateViewHolder(FindPetViewHolder viewHolder, Pet model, final int position) {
                viewHolder.setPetBreed(model.getPetBreed());
                viewHolder.setPetPrice(model.getPetPrice());
                viewHolder.setPetStatus(model.getPetStatus());
                final Pet petInfo = model;
                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PetDetails.class);
                        intent.putExtra("id",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.startListening();
        recyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


}
