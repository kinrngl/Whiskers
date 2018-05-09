package com.example.whiskersapp.petwhiskers;

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
        }

    }

    public void loginView(View view){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
