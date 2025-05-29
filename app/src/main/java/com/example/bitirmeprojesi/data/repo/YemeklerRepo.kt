package com.example.bitirmeprojesi.data.repo

import com.example.bitirmeprojesi.data.datasource.YemekDataSource
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import javax.inject.Inject


class YemeklerRepo @Inject constructor(val ds: YemekDataSource) {

    suspend fun yemekEkle(yemek_adi: String,
                          yemek_resim_adi: String,
                          yemek_fiyat: Int,
                          yemek_siparis_adet: Int,
                          kullanici_adi: String){
        ds.yemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
    }

    suspend fun tumYemekleriGetir() : List<Yemekler> = ds.tumYemekleriGetir()

    suspend fun sepettekileriGetir(kullanici_adi: String) : List<SepetYemekler> = ds.sepettekiYemekleriGetir(kullanici_adi) ?: emptyList()

    suspend fun sepettenYemekSil(sepetYemekID: String, kullanici_adi: String){
        ds.sepettenYemekSil(sepetYemekID, kullanici_adi)
    }

}