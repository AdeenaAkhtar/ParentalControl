package com.example.parentalcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    CheckBox robot;
    EditText edtEmail, edtPassword;
    FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Boolean role;
    int btnCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        robot = findViewById(R.id.checkBox);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
        auth = FirebaseAuth.getInstance();
    }
    public void goToSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpScreen.class);
        startActivity(intent);
        finish();
    }
    public void goToDashboardScreen(View view) {

        if(robot.isChecked()){
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (email.length() != 0 && password.length() != 0){
                auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            reference = FirebaseDatabase.getInstance().getReference("users");
                            userID = user.getUid();
                            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User userProfile = snapshot.getValue(User.class);
                                    if(userProfile != null){
                                        role = userProfile.isParent();
                                        if (role) {
                                            btnCount++;
                                            if(btnCount == 3){
                                            Intent intent = new Intent(getApplicationContext(), ParentDashboard.class);
                                            startActivity(intent);
                                            finish();}
                                            else{
                                                Toast.makeText(getApplicationContext(), "Incorrect Credentials " , Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Intent intent = new Intent(getApplicationContext(), ChildDashboard.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Incorrect Credentials " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Prove you are not a robot", Toast.LENGTH_SHORT).show();
        }
    }
}