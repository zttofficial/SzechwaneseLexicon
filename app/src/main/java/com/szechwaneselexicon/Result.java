package com.szechwaneselexicon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView result = findViewById(R.id.textView);
        TextView Paraphrase = findViewById(R.id.textView2);
        TextView Tone = findViewById(R.id.textView3);


        SQLite s = new SQLite();
        SQLiteDatabase db =s.openDatabase(getApplicationContext());
        Cursor cursor = db.rawQuery("select * from lexicon where character=?", new String[] {message});
        String paraphraseResult = null;
        String toneResult = null;
        while(cursor.moveToNext()){
            paraphraseResult = cursor.getString(cursor.getColumnIndex("paraphrase"));
            toneResult = cursor.getString(cursor.getColumnIndex("tone"));
        }

        result.setText(message);
        Paraphrase.setText(paraphraseResult);
        Tone.setText(toneResult);
        //System.out.print(paraphraseResult);
        //System.out.print(message);
        cursor.close();
        db.close();
    }
}
