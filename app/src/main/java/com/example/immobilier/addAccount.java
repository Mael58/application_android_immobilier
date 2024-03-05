package com.example.immobilier;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addAccount extends AppCompatActivity {
    private  FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Button inscription= findViewById(R.id.inscription);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText nomEditText= findViewById(R.id.Nom);
        EditText prenomEditText= findViewById(R.id.Prenom);
        EditText telEditText= findViewById(R.id.Tel);
        EditText emailEditText= findViewById(R.id.Email);
        EditText passEditText= findViewById(R.id.Password);







        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditText.getText().toString();
                String prenom =  prenomEditText.getText().toString();
                String tel = telEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passEditText.getText().toString();


                Map<String, Object> compte = new HashMap<>();
                compte.put("nom", nom);
                compte.put("prenom", prenom);
                compte.put("tel", tel);
                compte.put("email", email);
                compte.put("password", password);


                auth= FirebaseAuth.getInstance();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(addAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();

                                    db.collection("Compte").document(user.getUid()).set(compte);



                                    Intent intent= new Intent(addAccount.this, MainActivity.class);
                                    startActivity(intent);





                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(addAccount.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });







    }



}