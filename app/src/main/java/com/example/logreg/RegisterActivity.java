package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity
{

    private EditText etemail, etfelhasznalonev, etjelszo, etteljesNev;
    private Button regisztracio, vissza;
    DBHELPER adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        listeners();
    }

    private void listeners()
    {
        regisztracio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Regisztracio();
            }
        });

        vissza.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mainbe = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(mainbe);
                finish();
            }
        });
    }

    private void Regisztracio()
    {
        String email = etemail.getText().toString().trim();
        String felhasz = etfelhasznalonev.getText().toString().trim();
        String jelszo = etjelszo.getText().toString().trim();
        String teljesnev = etteljesNev.getText().toString().trim();

        // validálás
        if (email.isEmpty())
        {
            Toast.makeText(this, "E-mail megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (felhasz.isEmpty())
        {
            Toast.makeText(this, "Felhasználónév megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (jelszo.isEmpty())
        {
            Toast.makeText(this, "Jelszó megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (teljesnev.isEmpty())
        {
            Toast.makeText(this, "Teljes név megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adatbazis.regisztracio(email, felhasz, jelszo, teljesnev))
        {
            Toast.makeText(this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
        }
    }

    private void init()
    {
        etemail = findViewById(R.id.et_r_email);
        etfelhasznalonev = findViewById(R.id.et_r_felhasznalonev);
        etjelszo = findViewById(R.id.et_r_jelszo);
        etteljesNev = findViewById(R.id.et_r_teljesnev);
        regisztracio = findViewById(R.id.btn_r_regisztracio);
        vissza = findViewById(R.id.btn_r_vissza);

        adatbazis = new DBHELPER(RegisterActivity.this);
    }
}