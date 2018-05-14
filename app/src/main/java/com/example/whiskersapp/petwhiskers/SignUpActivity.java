package com.example.whiskersapp.petwhiskers;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whiskersapp.petwhiskers.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText contact;
    private EditText email;
    private EditText password;

    private FirebaseAuth userAuth;
    private DatabaseReference dbRef;

    ProgressDialog progressDialog;

    int result_auth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userAuth = FirebaseAuth.getInstance();

        if(userAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MenuActivity.class));

        }

        firstname = findViewById(R.id.signup_fname);
        lastname = findViewById(R.id.signup_lname);
        contact = findViewById(R.id.signup_contact);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        dbRef = FirebaseDatabase.getInstance().getReference("user_account");
        progressDialog = new ProgressDialog(this);
    }

    public void registerUser(View view){
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String contact_num = contact.getText().toString();
        String email_add = email.getText().toString();
        String pword = password.getText().toString();

        progressDialog.setMessage("Creating account...");
        progressDialog.show();

        if(!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(contact_num)
                && !TextUtils.isEmpty(email_add) && !TextUtils.isEmpty(pword)){
            progressDialog.dismiss();
            if(registerAuth(email_add,pword) == 1){
                String id = dbRef.push().getKey();
                User user = new User(id, fname, lname, contact_num, email_add, pword);
                dbRef.child(id).setValue(user);

                Toast.makeText(getApplicationContext(),"User Added!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"User authentication Failed!", Toast.LENGTH_LONG).show();
            }

        }

    }


    public void loginView(View view){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public int registerAuth(String email, String password){
        userAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Toast.makeText(getApplicationContext(),"User Authentication Successful!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error on Adding User Auth!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        result_auth=1;
        return result_auth;
    }
}
