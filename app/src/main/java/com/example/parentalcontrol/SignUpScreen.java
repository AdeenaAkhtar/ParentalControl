package com.example.parentalcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    CheckBox chkParent;
    EditText edtUsername,edtFirstName , edtLastName, edtPassword, edtConfirmPassword, edtNumber, edtAge;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        mAuth = FirebaseAuth.getInstance();
        chkParent = findViewById(R.id.chkParent);
        edtUsername = findViewById(R.id.edtUsername);
        edtFirstName =findViewById(R.id.edtFirstName) ;
        edtLastName=findViewById(R.id.edtLastName);
        edtPassword=findViewById(R.id.edtPassword);
        edtConfirmPassword=findViewById(R.id.edtConfirmPassword);
        edtNumber=findViewById(R.id.edtNumber);
        edtAge =findViewById(R.id.edtAge);
        edtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtAge.getText().toString().trim().length() != 0){
                if (Integer.parseInt(edtAge.getText().toString())>18)
                chkParent.setVisibility(View.VISIBLE);}
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void goToDashboardScreen(View view) {
        String username = edtUsername.getText().toString().trim();
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String password =  edtPassword.getText().toString().trim();
        String passwordConfirm =  edtConfirmPassword.getText().toString().trim();
        String number = edtNumber.getText().toString().trim();
        String age = edtAge.getText().toString().trim();

        if (username.length() != 0 && firstName.length() != 0 && lastName.length() != 0 && password.length() != 0 && passwordConfirm.length() != 0 && number.length() != 0 && age.length() != 0) {
            if (password.equals(passwordConfirm) && password.length()>6 ) {
                mAuth.createUserWithEmailAndPassword(username , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if (chkParent.isChecked()) {
                                user = new User(username, firstName, lastName, password, number, Integer.parseInt(age), true);
                            } else {
                                user = new User(username, firstName, lastName, password, number, Integer.parseInt(age), false);
                            }
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "SignUp Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    else {
                                        Toast.makeText(getApplicationContext(), "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "SignUp Unsuccessful " , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Passwords not valid", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}