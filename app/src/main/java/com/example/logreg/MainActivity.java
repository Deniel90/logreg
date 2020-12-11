package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private EditText felhasznalonev, jelszo;
    private Button bejelentkezes, regisztracio;
    DBHELPER adatbazis;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        listeners();
    }

    private void listeners()
    {
        bejelentkezes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bejelentkezes();
            }
        });

        regisztracio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent regisztraciora = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(regisztraciora);
                finish();
            }
        });
    }

    private void Bejelentkezes()
    {
        String felhasz = felhasznalonev.getText().toString().trim();
        String jelsz = jelszo.getText().toString().trim();

        // validálás
        if (felhasz.isEmpty())
        {
            Toast.makeText(this, "Felhasználónév megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (jelsz.isEmpty())
        {
            Toast.makeText(this, "Jelszó megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        // keresés az adatbázisban
        Cursor logAdatok = adatbazis.Kiiras(felhasz, jelsz);
        if (logAdatok == null)
        {
            Toast.makeText(this, "Hiba történt a bejelentkezés során!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (logAdatok.getCount() == 0)
        {
            Toast.makeText(this, "Nincs ilyen felhasználó", Toast.LENGTH_SHORT).show();
            return;
        }

        // teljes név lekérdezése
        StringBuilder builder = new StringBuilder();
        while (logAdatok.moveToNext())
        {
            builder.append("Üdvözöllek ").append(logAdatok.getString(0));
        }

        // teljes név átvitele a bejelentkezett oldalra
        sharedPreferences = getSharedPreferences("Adatok", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("nev", felhasznalonev.getText().toString());
        editor.apply();
        Intent loggedinre = new Intent(MainActivity.this, LoggedInActivity.class);
        startActivity(loggedinre);
        finish();
    }

    private void init()
    {
        felhasznalonev = findViewById(R.id.et_felhasznalonev);
        jelszo = findViewById(R.id.et_jelszo);
        bejelentkezes = findViewById(R.id.btn_bejelentkezes);
        regisztracio = findViewById(R.id.btn_regisztracio);

        adatbazis = new DBHELPER(MainActivity.this);
    }
}