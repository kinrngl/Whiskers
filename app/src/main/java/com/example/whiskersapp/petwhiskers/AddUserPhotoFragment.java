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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.example.whiskersapp.petwhiskers.Model.User;
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


public class AddUserPhotoFragment extends Fragment {
    private Button btnfilechoose;
    private Button btnAddPet;
    private ImageView imagePreview;
    private User user;

    private Uri imageUri;

    private StorageReference mStoreRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_account, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        user = new User();

        user.setFname(bundle.getString("firstname"));
        user.setLname(bundle.getString("lastname"));
        user.getContact(bundle.getString("contact"));
        user.getEmail(bundle.getString("email"));
        user.getPassword(bundle.getString("password"));

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user_account");
        mStoreRef = FirebaseStorage.getInstance().getReference("user");
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getContext());

        btnfilechoose = view.findViewById(R.id.user_btnupload);
        imagePreview = view.findViewById(R.id.user_image_preview);
        btnAddPet = view.findViewById(R.id.user_addEntry);

        btnfilechoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageFile();
            }
        });

        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Creating User Account...");
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

                            user.setId(id);
                            user.setImgUrl(taskSnapshot.getDownloadUrl().toString());

                            mDatabaseRef.child(id).setValue(user);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "User Added!", Toast.LENGTH_SHORT).show();
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