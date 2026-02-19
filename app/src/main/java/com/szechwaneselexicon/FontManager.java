package com.szechwaneselexicon;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.io.File;

/**
 * 字体管理工具类
 * 用于加载支持CJK扩展字符的字体
 */
public class FontManager {
    private static final String TAG = "FontManager";
    private static Typeface customTypeface = null;
    
    /**
     * 获取自定义字体
     * 支持CJK扩展A-F区域的汉字显示
     */
    public static Typeface getCustomTypeface(Context context) {
        if (customTypeface == null) {
            try {
                // 尝试从assets加载Noto Serif CJK SC字体
                customTypeface = Typeface.createFromAsset(
                    context.getAssets(), 
                    "fonts/NotoSerifCJKsc-Regular.otf"
                );
                Log.i(TAG, "自訂字體載入成功");
            } catch (Exception e) {
                Log.w(TAG, "自訂字體載入失敗，使用系統預設字體: " + e.getMessage());
                // 如果自定义字体加载失败，尝试使用系统的Serif字体
                customTypeface = Typeface.SERIF;
            }
        }
        return customTypeface;
    }
    
    /**
     * 检查字体文件是否存在
     */
    public static boolean isFontAvailable(Context context) {
        try {
            String[] fonts = context.getAssets().list("fonts");
            if (fonts != null) {
                for (String font : fonts) {
                    if (font.contains("NotoSerif") || font.contains("SourceHan")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "檢查字體檔案失敗: " + e.getMessage());
        }
        return false;
    }
}
