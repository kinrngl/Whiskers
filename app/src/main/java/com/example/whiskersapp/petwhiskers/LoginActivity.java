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

import com.example.whiskersapp.petwhiskers.Model.User;
import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private EditText email_add;
    private EditText pword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;

    int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("email",mAuth.getCurrentUser().getEmail());
            startActivity(intent);
        }

        progressDialog = new ProgressDialog(this);
        email_add = findViewById(R.id.login_email);
        pword = findViewById(R.id.login_password);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("user_account");
    }


    public void loginUser(View view) {
        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        String email = email_add.getText().toString();
        String password = pword.getText().toString();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please fill in the missing field/s",Toast.LENGTH_LONG).show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if(mAuth.getCurrentUser() != null){
                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra("email", mAuth.getCurrentUser().getEmail());
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Username or Password does not exist!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        progressDialog.dismiss();
    }


    public void signUpView(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
