package com.szechwaneselexicon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class Result extends AppCompatActivity {
    
    private TextView resultWord;
    private TextView paraphraseText;
    private TextView toneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        // 设置工具栏
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextViews
        resultWord = findViewById(R.id.textView);
        paraphraseText = findViewById(R.id.textView2);
        toneText = findViewById(R.id.textView3);
        
        // 应用自定义字体以支持CJK扩展字符
        applyCustomFont();

        SQLite s = new SQLite();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = s.openDatabase(getApplicationContext());
            if (db == null) {
                Toast.makeText(this, "資料庫開啟失敗", Toast.LENGTH_SHORT).show();
                resultWord.setText(message);
                paraphraseText.setText("無法載入資料");
                toneText.setText("無法載入資料");
                return;
            }
            
            cursor = db.rawQuery("select * from lexicon where character=?", new String[]{message});
            
            String paraphraseResult = null;
            String toneResult = null;
            
            if (cursor.moveToFirst()) {
                paraphraseResult = cursor.getString(cursor.getColumnIndexOrThrow("paraphrase"));
                toneResult = cursor.getString(cursor.getColumnIndexOrThrow("tone"));
            }

            // 设置显示内容
            resultWord.setText(message);
            
            if (paraphraseResult != null && !paraphraseResult.isEmpty()) {
                paraphraseText.setText(paraphraseResult);
            } else {
                paraphraseText.setText("暫無釋義");
                paraphraseText.setTextColor(getResources().getColor(R.color.textSecondary));
            }
            
            if (toneResult != null && !toneResult.isEmpty()) {
                toneText.setText(toneResult);
            } else {
                toneText.setText("暫無讀音");
                toneText.setTextColor(getResources().getColor(R.color.textSecondary));
            }
        } catch (Exception e) {
            Toast.makeText(this, "查詢發生錯誤：" + e.getMessage(), Toast.LENGTH_LONG).show();
            android.util.Log.e("Result", "資料庫查詢錯誤: " + e.getMessage());
            resultWord.setText(message);
            paraphraseText.setText("查詢失敗");
            toneText.setText("查詢失敗");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
    
    /**
     * 应用自定义字体以支持CJK扩展区域的特殊字符
     * 这对于显示四川方言中的生僻字至关重要
     */
    private void applyCustomFont() {
        try {
            Typeface customFont = FontManager.getCustomTypeface(this);
            
            // 应用字体到所有文本视图
            if (resultWord != null) {
                resultWord.setTypeface(customFont);
            }
            if (paraphraseText != null) {
                paraphraseText.setTypeface(customFont);
            }
            if (toneText != null) {
                toneText.setTypeface(customFont);
            }
        } catch (Exception e) {
            android.util.Log.w("Result", "字體應用失敗: " + e.getMessage());
        }
    }
}
