package com.example.whiskersapp.petwhiskers;

<<<<<<< HEAD
import android.os.Bundle;
=======
import android.annotation.SuppressLint;
import android.graphics.Color;

import android.os.Bundle;

>>>>>>> dev-eevee
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
<<<<<<< HEAD
import android.support.v7.widget.Toolbar;
=======
>>>>>>> dev-eevee
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD


=======
>>>>>>> dev-eevee


public class FindPetFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    MenuItem search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_pet, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        tabLayout = view.findViewById(R.id.findpet_tablayout);
        viewPager = view.findViewById(R.id.findpet_viewpager);

        PetViewPagerAdapter petAdapter = new PetViewPagerAdapter(getChildFragmentManager());

        petAdapter.getFragment(new FindCatFragment(), "Cat");
        petAdapter.getFragment(new FindDogFragment(), "Dog");

        viewPager.setAdapter(petAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
<<<<<<< HEAD

        search = menu.findItem(R.id.searchBar).setVisible(true);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Search for Breed...");
    }
=======
>>>>>>> dev-eevee

        MenuItem search = menu.findItem(R.id.searchBar).setVisible(true);
        search.setTitle("Search Breed...");
    }
}
