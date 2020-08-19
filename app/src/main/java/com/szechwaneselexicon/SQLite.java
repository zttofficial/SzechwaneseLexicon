package com.szechwaneselexicon;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SQLite {
    String filePath = "data/data/com.szechwaneselexicon/Szechwanese.db";
    String pathStr = "data/data/com.szechwaneselexicon";

    SQLiteDatabase database;
    public  SQLiteDatabase openDatabase(Context context){
        System.out.println("filePath:"+filePath);
        File jhPath=new File(filePath);
        if(jhPath.exists()){
            Log.i("Szechwanese", "exist");
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        }else{
            File path=new File(pathStr);
            Log.i("Szechwanese", "pathStr="+path);
            if (path.mkdir()){
                Log.i("Szechwanese", "Success");
            }else{
                Log.i("Szechwanese", "Fail");
            };
            try {
                AssetManager am= context.getAssets();
                InputStream is=am.open("Szechwanese.db");
                Log.i("Szechwanese", is+"");
                FileOutputStream fos=new FileOutputStream(jhPath);
                Log.i("Szechwanese", "fos="+fos);
                Log.i("Szechwanese", "jhPath="+jhPath);
                byte[] buffer=new byte[1024];
                int count = 0;
                while((count = is.read(buffer))>0){
                    Log.i("Szechwanese", "get");
                    fos.write(buffer,0,count);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            return openDatabase(context);
        }

    }
}
