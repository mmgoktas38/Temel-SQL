package com.example.sozlukuygulamasiiii;


    // Bu sınıfta veritabanında hangi işlemler yapılacaksa onun fonksiyonları oluşturulacak mesela kelimeEkle kelimeSil vb.

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class KelimelerDao {

    // KELİME EKLE FONKSİYONU

    public void kelimeEkle(VeriTabaniYardimcisi vt, String turkce, String ingilizce){

        SQLiteDatabase db = vt.getReadableDatabase();  // vt ile veritabanına ulaşıp onu yazılabilinir yapıyoruz, sorgu işlemeri için ise SQLiteDatabase sınıfından nesneye ihtiyacımız var onu oluşturduk
        ContentValues contentValues = new ContentValues();  // kişi turkce ve ingilizce veri ekleyecek bu gelenleri contentValues ile alıp tutuyoruz

        contentValues.put("turkce", turkce);    // kişinin girdiği turkce degeri turkce kolonuna yazcaz diye tuttuk
        contentValues.put("ingilizce", ingilizce);  // kişinin girdiği ingilizce degeri ingilizce kolonuna yazcaz diye tuttuk

        db.insertOrThrow("kelimeler",null, contentValues);  // Veritabanımızda kelimeler tablosuna contentValues i ekle dedik
        db.close();     // işlem bitince veritabanımızı kapatırız

    }


    // TÜM KELİMELERİ GETİRME FONKSİYONU

    public ArrayList<Kelimeler> tumKelimeler(VeriTabaniYardimcisi vt){      // VeritabaniYardimcisi ndan hep nesne almamız lazımki veritabanına erişebilelim

        ArrayList<Kelimeler> tumKelimeListesi = new ArrayList<>();
        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM kelimeler",null);     // rawQuery ile tablomuza istek attık hepsini istedik Curson ise satır satır okumayı sağlar

        while (c.moveToNext()){     // satır satır okurken diğerine her geçtiğinde dedik
            // gelen veri Kelimeler sınıfından kelime olacağı için o sınıftan bir nesne oluşturruz
            Kelimeler kelime = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id")),   // constructora göre girilecek ilk veri int c.getInt diyoruz o yuzden sonra içine cçgetColumnIndex diyerek o kolonun adını yazıyoruz orda ne varsa sırayla getiriyor
                    c.getString(c.getColumnIndex("turkce")),                // burada ki altı çizili kırmızılıkta hata yok, anrdoid studio hatasıdır o
                    c.getString(c.getColumnIndex("ingilizce")));

            tumKelimeListesi.add(kelime);
        }
        return tumKelimeListesi;
    }

    // GİRİLEN kelime_id VERİSİNE GÖRE KELİME SİLME

    public void kelimeSil(VeriTabaniYardimcisi vt, int kelime_id){
        SQLiteDatabase db = vt.getWritableDatabase();       // veritabanına erişiyoruz
        db.delete("kelimeler","kelime_id=?",new String[]{String.valueOf(kelime_id)});       // kelimeler tablosundan where şartı kelime_id=?  en son virgül ise girilen kelime id sini yazıyoruz
        db.close();
    }

    // KELİME GÜNCELLE FONKSİYONU

    public void kelimeGuncelle(VeriTabaniYardimcisi vt, int kelime_id, String turkce, String ingilizce){
        SQLiteDatabase db = vt.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("turkce", turkce);
        contentValues.put("ingilizce", ingilizce);

        db.update("kelimeler", contentValues, "kelime_id=?", new String[]{String.valueOf(kelime_id)});
        db.close();
    }

    // TABLOMUZDA KAÇ VERİ KAYILI FONKSİYONU

    public int veriSayisi(VeriTabaniYardimcisi vt){

        int toplam = 0;
        SQLiteDatabase db = vt.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) as toplam FROM kelimeler", null);       // 1 kolon getirecek zaten o kolonun adı toplam olsun dedik
        while (c.moveToNext()){
            toplam = c.getInt(c.getColumnIndex("toplam"));      // int türünde 1 kolon getirecek adı toplam olan bir kolon bu
        }
        return toplam;
    }

    public Kelimeler kelimeGetir(VeriTabaniYardimcisi vt, int kelime_id){
        Kelimeler kelime = null;
        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM kelimeler WHERE kelime_id="+kelime_id,null);
        while (c.moveToNext()){
            kelime = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id")),
                    c.getString(c.getColumnIndex("turkce")),
                    c.getString(c.getColumnIndex("ingilizce")));
        }
        return kelime;
    }

    // RASTGELE 5 KELİME GETİR FONKSİYON

    public ArrayList<Kelimeler> rastgele5Kelime(VeriTabaniYardimcisi vt){
        SQLiteDatabase db = vt.getWritableDatabase();
        ArrayList<Kelimeler> random5Kelime = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM kelimeler ORDER BY RANDOM() LIMIT 5", null);

        while (c.moveToNext()){
            Kelimeler kelime = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id")),
                    c.getString(c.getColumnIndex("turkce")),
                    c.getString(c.getColumnIndex("ingilizce")));
            random5Kelime.add(kelime);
        }
        return random5Kelime;
    }


    // KELİME GETİRME FONKSİYON

    public ArrayList<Kelimeler> kelimeAra(VeriTabaniYardimcisi vt, String keyWord){
        SQLiteDatabase db = vt.getWritableDatabase();
        ArrayList<Kelimeler> kelimeListesi = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM kelimeler WHERE turkce like '%"+keyWord+"%'", null);

        while (c.moveToNext()){
            Kelimeler kelime = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id")),
                    c.getString(c.getColumnIndex("turkce")),
                    c.getString(c.getColumnIndex("ingilizce")));
            kelimeListesi.add(kelime);
        }
        return kelimeListesi;

    }

}
