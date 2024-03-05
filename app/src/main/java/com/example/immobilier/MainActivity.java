package com.example.immobilier;

import static android.content.ContentValues.TAG;



import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseFirestore db;
    boolean admin_mode = false;
    Adapter adapter;
    Button supprimer;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.List);
        db = FirebaseFirestore.getInstance();
        supprimer = findViewById(R.id.supprimer);


        imageView = findViewById(R.id.plus);
        if (getIntent().hasExtra("ADMIN_MODE")) {
            admin_mode = getIntent().getBooleanExtra("ADMIN_MODE", true);

        }
        auth = FirebaseAuth.getInstance();

        if (admin_mode == true) {

            getAnnonces(auth.getUid());
            supprimer.setVisibility(View.VISIBLE);


        } else {
            getAnnonces(null);


        }


        adapter = new Adapter(this, new ArrayList<Bien>());

        listView.setAdapter(adapter);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPopUp(null);


            }
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, List<String>> list = adapter.getIdList();
                for (Map.Entry<String, List<String>> entry : list.entrySet()) {
                    String typeBien = entry.getKey();
                    List<String> idList = entry.getValue();

                    for (String id : idList) {
                        db.collection(typeBien).document(id)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                        getAnnonces(auth.getUid());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }

                }


            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_button:
                popUp();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




    public void popUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Options de compte");


        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {

            builder.setNeutralButton("Deconnexion", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FirebaseAuth.getInstance().signOut();
                    recreate();

                }
            });

            builder.setPositiveButton("Gestion des annonces", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("ADMIN_MODE", true);
                    startActivity(intent);


                }
            });
        } else {
            builder.setNegativeButton("Créer un compte", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, addAccount.class);
                    startActivity(intent);


                }
            });


            builder.setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, seConnecter.class);
                    startActivity(intent);
                    finish();

                }
            });
            builder.setNeutralButton("Continuer en tant qu'invité", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                }
            });
        }


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    public void addPopUp(Bien bien) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajout d'une annonce");
        View view = getLayoutInflater().inflate(R.layout.add_annonces, null);
        builder.setView(view);
        Spinner etat = view.findViewById(R.id.Etat);
        Spinner type = view.findViewById(R.id.type);


        EditText adresseEditText = view.findViewById(R.id.Address);
        EditText prixEditText = view.findViewById(R.id.prix);
        EditText nbPieceEditText = view.findViewById(R.id.nbPiece);
        EditText descriptionEditText = view.findViewById(R.id.Description);
        EditText surfaceEditText = view.findViewById(R.id.SurfaPieces);
        EditText villeEditText = view.findViewById(R.id.ville);
        EditText cpEditText = view.findViewById(R.id.cp);
        RecyclerView recyclerViewTags = view.findViewById(R.id.recyclerViewTags);



        Button addAnnonce = view.findViewById(R.id.btnAddAnnonce);
        Button files= view.findViewById(R.id.files);
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile= new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile= Intent.createChooser(chooseFile, "choose a file");
                startActivity(chooseFile);
            }
        });


        if (bien != null) {
            adresseEditText.setText(bien.getAdresse());
            prixEditText.setText(bien.getPrix());
            nbPieceEditText.setText(bien.getNbPiece());
            descriptionEditText.setText(bien.getDescription());
            surfaceEditText.setText(bien.getSurface());
            villeEditText.setText(bien.getVille());
            cpEditText.setText(bien.getCp());



        }


        ArrayAdapter<CharSequence> adapterEtat = ArrayAdapter.createFromResource(MainActivity.this, R.array.Etat, android.R.layout.simple_spinner_item);
        adapterEtat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etat.setAdapter(adapterEtat);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(MainActivity.this, R.array.Type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);

        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("Garage"));
        tagList.add(new Tag("Piscine"));
        tagList.add(new Tag("Jardin"));
        tagList.add(new Tag("Cave"));
        tagList.add(new Tag("Balcon"));
        tagList.add(new Tag("Terrasse"));
        tagList.add(new Tag("Ascenseur"));
        tagList.add(new Tag("Parking"));
        tagList.add(new Tag("Meublé"));
        tagList.add(new Tag("Non meublé"));
        tagList.add(new Tag("Etage"));
        tagList.add(new Tag("Rez-de-chaussée"));







        TagAdapter tagAdapter = new TagAdapter(tagList);
        recyclerViewTags.setAdapter(tagAdapter);
        recyclerViewTags.setLayoutManager(new LinearLayoutManager(this));




        AlertDialog alertDialog = builder.create();


        addAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bien bienToHandle = new Bien();


                bienToHandle.setAdresse(adresseEditText.getText().toString());
                bienToHandle.setPrix(prixEditText.getText().toString());
                bienToHandle.setNbPiece(nbPieceEditText.getText().toString());
                bienToHandle.setDescription(descriptionEditText.getText().toString());
                bienToHandle.setSurface(surfaceEditText.getText().toString());
                bienToHandle.setEtat(etat.getSelectedItem().toString());
                bienToHandle.setTypeBien(type.getSelectedItem().toString());
                bienToHandle.setCp(cpEditText.getText().toString());
                bienToHandle.setVille(villeEditText.getText().toString());
                bienToHandle.setTags(tagAdapter.getTags());




                bienToHandle.setUserId(auth.getCurrentUser().getUid());

                CollectionReference collectionReference = db.collection(bienToHandle.getTypeBien().equals("Maison") ? "Maison" : "Appartements");

                if (bien == null) {
                    collectionReference.add(bienToHandle);
                } else {

                    collectionReference.document(bien.getId()).set(bienToHandle);
                }


                alertDialog.dismiss();
            }

        });


        alertDialog.show();

    }

    @Override
    protected void onStart() {
        ImageView imageView = findViewById(R.id.plus);
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            imageView.setVisibility(View.VISIBLE);
        }
    }


    private void getAnnonces(String userId) {
        CollectionReference query = db.collection("Appartements");
        Task<QuerySnapshot> queryGet;
        if (userId != null) {
            queryGet = query.whereEqualTo("userId", userId).get();
        } else {
            queryGet = query.get();
        }

        queryGet.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Bien> biens = new ArrayList<Bien>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Bien b = document.toObject(Bien.class);
                        b.setId(document.getId());
                        biens.add(b);
                    }
                    adapter.setAppts(biens);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


    }
}

