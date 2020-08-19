package com.szechwaneselexicon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.SzechwaneseLexicon.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button retrieve = (Button) findViewById(R.id.retrieve);

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Result.class);
                EditText type = (EditText) findViewById(R.id.type);
                String inputText = type.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, inputText);
                startActivity(intent);
            }
        });

    }



}