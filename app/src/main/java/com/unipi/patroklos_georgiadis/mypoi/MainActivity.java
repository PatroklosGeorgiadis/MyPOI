package com.unipi.patroklos_georgiadis.mypoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("DBNew.db",MODE_PRIVATE,null);
        db.execSQL("Create table if not exists POI("+
                "title TEXT PRIMARY KEY, timestamp TEXT, location TEXT," +
                "category TEXT, description TEXT)");
    }

    public void go1(View view){
        Intent intent = new Intent(MainActivity.this, SearchPOI.class);
        startActivity(intent);
    }

    public void go2(View view){
        Intent intent = new Intent(MainActivity.this, ViewingPOI.class);
        startActivity(intent);
    }

    public void go3(View view){
        Intent intent = new Intent(MainActivity.this, CreatePOI.class);
        startActivity(intent);
        //String newEmail = "stanley@unipi.gr";
        //String fullName = "Stanley Jim";
        //db.execSQL("Insert into USER Values(?,?)",new String[]{newEmail,fullName});
    }

}