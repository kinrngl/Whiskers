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
import android.support.v7.widget.CardView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class AddPetPhotoFragment extends Fragment {
    private Button btnfilechoose;
    private Button btnAddPet;
    private ImageView imagePreview;
    private Pet pet;

    private Uri imageUri;

    private StorageReference mStoreRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_pet, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        pet = new Pet();

        pet.setPet_name(bundle.getString("name"));
        pet.setBreed(bundle.getString("breed"));
        pet.setFurcolor(bundle.getString("furcolor"));
        pet.setEyecolor(bundle.getString("eyecolor"));
        pet.setGender(bundle.getString("gender"));
        pet.setCategory(bundle.getString("category"));
        pet.setBirthdate(bundle.getString("bday"));
        pet.setDetails(bundle.getString("desc"));
        pet.setTransaction(bundle.getString("trans"));
        pet.setIsAdopt("no");
        pet.setVerStat("0");

        Date tentime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        final String time = df.format(tentime);

        pet.setDatePost(time);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pet");
        mStoreRef = FirebaseStorage.getInstance().getReference("pet_entry");
        mAuth = FirebaseAuth.getInstance();

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
                uploadFile();
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

    private void uploadFile(){
        if(imageUri != null){
            StorageReference fileRef = mStoreRef.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String id = mDatabaseRef.push().getKey();

                            pet.setId(id);
                            pet.setImgUrl(taskSnapshot.getDownloadUrl().toString());
                            pet.setIsAdopt("yes");
                            pet.setOwner_id(mAuth.getCurrentUser().getUid());
                            pet.setStatus("available");

                            mDatabaseRef.child(id).setValue(pet);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Pet Added!", Toast.LENGTH_SHORT).show();

                            Fragment fragment = new PetFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.contentFrame, fragment);
                            fragmentTransaction.commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),"Error in uploading image.", Toast.LENGTH_SHORT).show();
                        }
                    });

            progressDialog.dismiss();
        }else{
            Toast.makeText(getContext(), "No file uploaded.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}