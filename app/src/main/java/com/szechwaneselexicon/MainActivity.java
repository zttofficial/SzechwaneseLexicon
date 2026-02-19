package com.szechwaneselexicon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.SzechwaneseLexicon.MESSAGE";
    private TextInputEditText inputEditText;
    private MaterialButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 应用自定义字体以支持CJK扩展字符
        applyCustomFont();

        inputEditText = findViewById(R.id.type);
        searchButton = findViewById(R.id.retrieve);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = inputEditText.getText().toString().trim();
                
                if (inputText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "請輸入要查詢的詞語", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Result.class);
                intent.putExtra(EXTRA_MESSAGE, inputText);
                startActivity(intent);
            }
        });
    }
    
    /**
     * 应用自定义字体以支持CJK扩展区域的特殊字符
     */
    private void applyCustomFont() {
        try {
            Typeface customFont = FontManager.getCustomTypeface(this);
            
            // 应用字体到标题
            TextView appTitle = findViewById(R.id.appTitle);
            if (appTitle != null) {
                appTitle.setTypeface(customFont);
            }
            
            // 应用字体到输入框
            if (inputEditText != null) {
                inputEditText.setTypeface(customFont);
            }
        } catch (Exception e) {
            // 如果字体应用失败，静默处理，使用系统默认字体
            android.util.Log.w("MainActivity", "字體應用失敗: " + e.getMessage());
        }
    }
}