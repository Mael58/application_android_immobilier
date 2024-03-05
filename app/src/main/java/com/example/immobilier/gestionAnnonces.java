package com.example.immobilier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class gestionAnnonces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_annonces);
        ListView listGestion= findViewById(R.id.listeGestion);

        List<Bien> bienList= new ArrayList<>();



    }
}