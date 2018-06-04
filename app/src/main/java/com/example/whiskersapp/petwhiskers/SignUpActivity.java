package com.example.whiskersapp.petwhiskers;

<<<<<<< Updated upstream
=======
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
>>>>>>> Stashed changes
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText contact;
    private EditText email;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = findViewById(R.id.signup_fname);
        lastname = findViewById(R.id.signup_lname);
        contact = findViewById(R.id.signup_contact);
        email = findViewById(R.id.signup_email);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MenuActivity.class));

<<<<<<< Updated upstream
        }
    }

    public void proceedView(View view){
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String contact_num = contact.getText().toString();
        String email_add = email.getText().toString();

        if(!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(contact_num) && !TextUtils.isEmpty(email_add)){
            Intent intent = new Intent(SignUpActivity.this,SignUpConActivity.class);

            intent.putExtra("fname",fname);
            intent.putExtra("lname",lname);
            intent.putExtra("contact",contact_num);
            intent.putExtra("email",email_add);

            startActivity(intent);
        }else{
            Toast.makeText(this,"Fill up the blanks",Toast.LENGTH_LONG).show();
=======
    public void registerUser(View view) {
        final String fname = firstname.getText().toString();
        final String lname = lastname.getText().toString();
        final String contact_num = contact.getText().toString();
        final String email_add = email.getText().toString();
        final String pword = password.getText().toString();

      //  progressDialog.setMessage("Creating account...");
      //  progressDialog.show();

        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(contact_num)
                && !TextUtils.isEmpty(email_add) && !TextUtils.isEmpty(pword)) {
            /*if(registerAuth(email_add,pword) == 1){
                String id = userAuth.getUid();
                User user = new User(id,fname,lname,contact_num,email_add,pword);
                dbRef.child(id).setValue(user);

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"User Added!", Toast.LENGTH_LONG).show();
            }else{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"User authentication Failed!", Toast.LENGTH_LONG).show();
            })*/


           /* userAuth.createUserWithEmailAndPassword(email_add, pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String id = userAuth.getUid();
                        User user = new User(id,fname,lname,contact_num,email_add,pword);
                        dbRef.child(id).setValue(user);

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"User Added!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"User authentication Failed!", Toast.LENGTH_LONG).show();
                    }
                }
            }); */
            Fragment fragment = new AddUserPhotoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("firstname", fname);
            bundle.putString("lastname", lname);
            bundle.putString("contact", contact_num);
            bundle.putString("email", email_add);
            bundle.putString("password", pword);

            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        } else {
            Toast.makeText(getContext(), "Please fill up the fields.", Toast.LENGTH_SHORT).show();
>>>>>>> Stashed changes
        }
    }

    }

    public void loginView(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtras()
        startActivity(intent);
    }
}
