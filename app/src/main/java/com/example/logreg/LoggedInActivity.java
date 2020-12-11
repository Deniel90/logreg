package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity
{

    private TextView bejelentkezettAdatok;
    private Button kijelentkezes;
    private String bejelentkezettnev;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        init();

        kijelentkezes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mainbe = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(mainbe);
                finish();
            }
        });
    }

    private void init()
    {
        bejelentkezettAdatok = findViewById(R.id.tv_l_bejelentkezettAdatok);
        kijelentkezes = findViewById(R.id.btn_l_kijelentkezes);

        SharedPreferences sharedPreferences = getSharedPreferences("Adatok", Context.MODE_PRIVATE);
        bejelentkezettnev = sharedPreferences.getString("nev","Nincs elmentve a neved!");
        bejelentkezettAdatok.setText(bejelentkezettnev);
    }
}