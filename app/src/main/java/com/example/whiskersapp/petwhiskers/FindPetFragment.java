package com.example.whiskersapp.petwhiskers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_pet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.findpet_tablayout);
        viewPager = view.findViewById(R.id.findpet_viewpager);

        PetViewPagerAdapter petAdapter = new PetViewPagerAdapter(getChildFragmentManager());

        petAdapter.getFragment(new FindCatFragment(), "Cat");
        petAdapter.getFragment(new FindDogFragment(), "Dog");

        viewPager.setAdapter(petAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
