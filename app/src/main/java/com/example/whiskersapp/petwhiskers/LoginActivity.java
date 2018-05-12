package com.example.whiskersapp.petwhiskers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private EditText email_add;
    private EditText pword;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        email_add = findViewById(R.id.login_email);
        pword = findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MenuActivity.class));

        }

    }

    public void loginUser(View view) {
        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        String email = email_add.getText().toString();
        String password = pword.getText().toString();
        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please fill in the missing field/s",Toast.LENGTH_LONG).show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Username or Password does not exist!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
