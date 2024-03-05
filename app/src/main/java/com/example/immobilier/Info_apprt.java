package com.example.immobilier;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Info_apprt extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mail;
    TextView tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_apprt);
        TextView prix = findViewById(R.id.prix);
        TextView piece = findViewById(R.id.pieces);
        TextView surface = findViewById(R.id.surface);
        TextView chambre = findViewById(R.id.chambre);
        TextView titre = findViewById(R.id.titre);
        RecyclerView listView = findViewById(R.id.listPicture);
        mail = findViewById(R.id.email);
        tel = findViewById(R.id.tel);

        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ tel.getText().toString()));
                startActivity(intent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+ mail.getText().toString()));
                startActivity(intent);
            }
        });




        Bien bien = (Bien) getIntent().getSerializableExtra("appt");
        getUserDetails(bien);

        //System.out.println(appartement.url);
        surface.setText(bien.getSurface() + " m²");
        prix.setText(bien.getPrix() + " €");
        chambre.setText(bien.getChambres() + " chambres");
        piece.setText(bien.getNbPiece() + " pieces");


        PictureAdapter2 adapter = new PictureAdapter2(bien.getUrl());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(adapter);


        titre.setText(bien.getTitle());

    }

    private void getUserDetails(Bien bien) {
        if(bien.getUserId() == null){
            return;
        }

        db.collection("Compte").document(bien.getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    tel.setText(document.get("tel").toString());
                    mail.setText(document.get("email").toString());

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


    }
}