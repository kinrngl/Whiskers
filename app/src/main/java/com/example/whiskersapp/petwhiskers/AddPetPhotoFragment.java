package com.example.whiskersapp.petwhiskers;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class AddPetPhotoFragment extends Fragment {
    private Button btnfilechoose;
    private Button btnAddPet;
    private ImageView imagePreview;

    private Uri imageUri;

    private ProgressDialog progressDialog;

    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_pet, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = getArguments();
        progressDialog = new ProgressDialog(getContext());

        btnfilechoose = view.findViewById(R.id.pet_btnupload);
        imagePreview = view.findViewById(R.id.pet_image_preview);
        btnAddPet = view.findViewById(R.id.pet_addEntry);

        btnfilechoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageFile();
            }
        });

        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Creating Pet Entry...");
                progressDialog.show();
                proceedRec();
            }
        });

    }

    public void openImageFile(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            //Picasso.with(getContext()).load(imageUri).into(imagePreview);
            imagePreview.setImageURI(imageUri);
        }
    }

    private void proceedRec(){
        if(imageUri != null){
            String imgUri = imageUri.toString();
            bundle.putString("imgURI",imgUri );
            if(imgUri != null){
                Log.d("check uri", "not null");
            }else{
                Log.d("check uri", " null");

            }
            progressDialog.dismiss();
            Fragment fragment = new AddLocationFragment();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();


        }else{
            Toast.makeText(getContext(), "No file uploaded.", Toast.LENGTH_SHORT).show();
        }
    }

}