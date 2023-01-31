package com.unipi.patroklos_georgiadis.mypoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewingPOI extends AppCompatActivity {
    LinearLayout layout;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_poi);

        layout = findViewById(R.id.container);

        db = openOrCreateDatabase("DBNew.db",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("Select * from POI",null);
        while (cursor.moveToNext()){
            //builder.append("Email:").append(cursor.getString((0))).append("\n");
            String p1 = cursor.getString(0);
            String p1b = cursor.getString(3);
            String p2 = cursor.getString(1);
            String p3 = cursor.getString(2);
            String p4 = cursor.getString(4);
            addCard(p1,p2,p3,p4,p1b);
        }

    }

    private void addCard(String s1,String s2, String s3, String s4, String s1b) {
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
                Intent intent = new Intent(ViewingPOI.this, CreatePOI.class);
                intent.putExtra("title",s1);
                intent.putExtra("description",s4);
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