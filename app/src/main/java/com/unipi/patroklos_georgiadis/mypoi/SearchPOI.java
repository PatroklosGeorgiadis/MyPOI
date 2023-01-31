package com.unipi.patroklos_georgiadis.mypoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchPOI extends AppCompatActivity {
    LinearLayout layout;
    SQLiteDatabase db;
    String s1,s1b,s2,s3,s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_poi);

        layout = findViewById(R.id.container);
    }

    public void searchPOI(View vi){
        layout.removeAllViews();
        EditText titleSearch = (EditText)findViewById(R.id.textInputEditText);
        db = openOrCreateDatabase("DBNew.db",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("Select * from POI where title like \"%"+titleSearch.getText().toString()+"%\"",null);
        while (cursor.moveToNext()){
            s1 = cursor.getString(0);
            s1b = cursor.getString(3);
            s2 = cursor.getString(1);
            s3 = cursor.getString(2);
            s4 = cursor.getString(4);
        }
        cursor.close();
        final View view = getLayoutInflater().inflate(R.layout.poi, null);

        TextView nameView1 = view.findViewById(R.id.name);
        TextView nameView2 = view.findViewById(R.id.name2);
        TextView nameView3 = view.findViewById(R.id.name4);
        TextView nameView4 = view.findViewById(R.id.description);
        Button edit = view.findViewById(R.id.edit);
        Button delete = view.findViewById(R.id.delete);

        nameView1.setText(s1+", "+s1b);
        nameView2.setText(s2);
        nameView3.setText(s3);
        nameView4.setText(s4);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPOI.this, CreatePOI.class);
                intent.putExtra("title",s1);
                intent.putExtra("description",s1b);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("POI","title=?",new String[]{s1});
                layout.removeView(view);
            }
        });

        layout.addView(view);
    }
}