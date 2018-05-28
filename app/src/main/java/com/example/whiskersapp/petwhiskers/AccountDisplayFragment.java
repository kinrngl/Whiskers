package com.example.whiskersapp.petwhiskers;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.example.whiskersapp.petwhiskers.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountDisplayFragment extends Fragment {
    private Button editProfile;
    private TextView header_name;
    private TextView fname;
    private TextView lname;
    private TextView contact;
    private TextView email;
    private Button removeProfile;

    private AlertDialog.Builder choice;
    private AlertDialog alert;

    FirebaseDatabase fbData;
    FirebaseAuth mAuth;
    DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account_display, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editProfile = view.findViewById(R.id.acctdisplay_edit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.contentFrame, new EditAccountFragment()).addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });

        fname = view.findViewById(R.id.acctdisplay_fname);
        lname = view.findViewById(R.id.acctdisplay_lname);
        contact = view.findViewById(R.id.acctdisplay_contact);
        email = view.findViewById(R.id.acctdisplay_email);
        header_name = view.findViewById(R.id.acct_header_name);

        mAuth = FirebaseAuth.getInstance();
        fbData = FirebaseDatabase.getInstance();
        dbRef = fbData.getReference("user_account");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);

                    if(mAuth.getCurrentUser().getEmail().equals(user.getEmail())){
                        header_name.setText(user.getFname()+" "+user.getLname());
                        fname.setText(user.getFname());
                        lname.setText(user.getLname());
                        contact.setText(user.getContact());
                        email.setText(user.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        choice = new AlertDialog.Builder(getView().getContext());
        choice.setTitle("Are you sure to delete your account?");

        choice.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                dbRef.child(mAuth.getCurrentUser().getUid()).removeValue();
                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getView().getContext(),"Account Deleted!",Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                            Intent intent = new Intent(getContext(), StartActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                //Toast.makeText(getView().getContext(),"Account Deleted!",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        choice.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert = choice.create();

        removeProfile = view.findViewById(R.id.acctdisplay_remove);
        removeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });
    }

}
