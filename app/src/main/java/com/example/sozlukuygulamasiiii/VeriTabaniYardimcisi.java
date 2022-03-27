package com.example.sozlukuygulamasiiii;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


        // Bu sınıf veritabanımızı oluşturmamızı ve içinde tablo oluşturmamızı sağlıyor

public class VeriTabaniYardimcisi extends SQLiteOpenHelper {
    public VeriTabaniYardimcisi(@Nullable Context context) {
        super(context, "sozluk", null, 1);  // veritabanımızın adı sozluk versiyonu 1
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE kelimeler (kelime_id INTEGER PRIMARY KEY AUTOINCREMENT, turkce TEXT, ingilizce TEXT);");  // veritabanına tablomuzu ve kolonlarını oluşturduk
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS kelimeler");
        onCreate(sqLiteDatabase);

    }
}
