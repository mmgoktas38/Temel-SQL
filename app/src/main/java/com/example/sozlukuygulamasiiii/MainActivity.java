package com.example.sozlukuygulamasiiii;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    VeriTabaniYardimcisi vt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vt = new VeriTabaniYardimcisi(this);

        // kelime ekliyoruz
        /*new KelimelerDao().kelimeEkle(vt,"kapı","door");
        new KelimelerDao().kelimeEkle(vt,"deniz","sea");
        new KelimelerDao().kelimeEkle(vt,"kalem","pencil");
        new KelimelerDao().kelimeEkle(vt,"bilgisayar","computer");
        new KelimelerDao().kelimeEkle(vt,"yazılım","software");*/


        new KelimelerDao().kelimeSil(vt,1);
        new KelimelerDao().kelimeGuncelle(vt,2,"denizz","seaa");

        // veri sayısını getir
        int veriSayisi = new KelimelerDao().veriSayisi(vt);
        Log.e("kelime adedi: ", String.valueOf(veriSayisi));

        // kelime id ye göre kelime getir
        Kelimeler kelime = new KelimelerDao().kelimeGetir(vt, 2);
        Log.e("kelime : ", kelime.getTurkce() + " - " + kelime.getIngilizce() );

        // tum kelimeler bir arraylist te geleceği için Kelimeler sınıfından bir arraylist oluşturup o şekilde alıyoruz
        ArrayList<Kelimeler> gelenTumKelimeler = new KelimelerDao().tumKelimeler(vt);

        // gelen kelimelistesini for each yapısı ile çekiyoruz
        for (Kelimeler k:gelenTumKelimeler){
            Log.e("gelen kelime: "+ k.getKelime_id(), ""+k.getTurkce() + " - " + k.getIngilizce());
        }


        // rastgele 5 kelime getir
        ArrayList<Kelimeler> gelenRastgele5Kelime = new KelimelerDao().rastgele5Kelime(vt);
        for (Kelimeler k:gelenRastgele5Kelime){
            Log.e("rastgele 5 kelime: "+ k.getKelime_id(), ""+k.getTurkce() + " - " + k.getIngilizce());
        }

        // aranacak kelimeye göre kelimeleri getir
        ArrayList<Kelimeler> arnacakKelimeler = new KelimelerDao().kelimeAra(vt, "kalem");
        for (Kelimeler k:arnacakKelimeler){
            Log.e(k.getIngilizce(),k.getTurkce());
        }



    }
}