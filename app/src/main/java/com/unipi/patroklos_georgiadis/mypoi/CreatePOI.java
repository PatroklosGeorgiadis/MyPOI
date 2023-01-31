package com.unipi.patroklos_georgiadis.mypoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

public class CreatePOI extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    private int locationRequestCode;
    String location_data;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poi);

        Intent intent = getIntent();
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText(intent.getStringExtra("title"));
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        editText2.setText(intent.getStringExtra("description"));
        Spinner category = (Spinner)findViewById(R.id.spinner);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},locationRequestCode);
            return;
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        db = openOrCreateDatabase("DBNew.db",MODE_PRIVATE,null);
    }

    public void addPOI(View view){
        locationFinder();
        EditText editText = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        Spinner category = (Spinner)findViewById(R.id.spinner);
        Date currentTime = Calendar.getInstance().getTime();
        if(editText.getText().toString()!=""){
            db.execSQL("Insert or replace into POI Values(?,?,?,?,?)",new String[]{editText.getText().toString(),
                    currentTime.toString(),location_data,category.getSelectedItem().toString(),editText2.getText().toString()});
            showMessage("Result","Successfully added");
        }
        else{
            showMessage("Result","Please fill the title field");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==123){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void locationFinder() {
        locationRequestCode = 123;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},locationRequestCode);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        location_data = loc.getLatitude()+","+loc.getLongitude();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        location_data = location.getLatitude()+","+location.getLongitude();
    }

    public void showMessage(String title, String text){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(text)
                .show();
    }
}