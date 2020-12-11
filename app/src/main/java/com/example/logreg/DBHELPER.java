package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHELPER extends SQLiteOpenHelper
{
    public static final String  DB_NAME = "logok.db";



    public DBHELPER(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE IF NOT EXISTS felhasznalo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email VARCHAR(255) UNIQUE NOT NULL," +
                "felhnev VARCHAR(255) UNIQUE NOT NULL," +
                "jelszo VARCHAR(255) NOT NULL," +
                "teljesnev VARCHAR(255) NOT NULL" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    public boolean regisztracio(String email, String felhnev, String jelszo, String teljesnev)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues adatok = new ContentValues();
        adatok.put("email", email);
        adatok.put("felhnev", felhnev);
        adatok.put("jelszo", jelszo);
        adatok.put("teljesnev", teljesnev);

        return db.insert("felhasznalo", null, adatok) != -1;
    }

    public Cursor Kiiras(String felhasznalo, String jelsz)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        if (felhasznalo.contains("@"))
        {
            return db.rawQuery("SELECT teljesnev FROM felhasznalo WHERE email = ? AND jelszo = ?", new String[]{felhasznalo, jelsz});
        }
        else
        {
            return db.rawQuery("SELECT teljesnev FROM felhasznalo WHERE felhnev = ? AND jelszo = ?", new String[]{felhasznalo, jelsz});
        }
    }
}
