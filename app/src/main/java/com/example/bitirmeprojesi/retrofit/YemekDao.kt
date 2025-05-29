package com.example.bitirmeprojesi.retrofit

import com.example.bitirmeprojesi.data.entity.CRUDCevap
import com.example.bitirmeprojesi.data.entity.SepetYemeklerCevap
import com.example.bitirmeprojesi.data.entity.TumYemekleriGetir
import com.example.bitirmeprojesi.data.entity.Yemekler
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface YemekDao {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun tumYemekler() : TumYemekleriGetir

    @FormUrlEncoded
    @POST("yemekler/sepeteYemekEkle.php")
    suspend fun sepeteYemekEkle(@Field("yemek_adi") yemek_adi: String,
                                @Field("yemek_resim_adi") yemek_resim_adi: String,
                                @Field("yemek_fiyat") yemek_fiyat: Int,
                                @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
                                @Field("kullanici_adi") kullanici_adi: String
                                ): CRUDCevap

    @FormUrlEncoded
    @POST("yemekler/sepettekiYemekleriGetir.php")
    suspend fun sepettekiYemekleriGetir(@Field("kullanici_adi") kullanici_adi: String) : SepetYemeklerCevap

    @FormUrlEncoded
    @POST("yemekler/sepettenYemekSil.php")
    suspend fun sepettenYemekSil(@Field("sepet_yemek_id") sepet_yemek_id: String,
                                 @Field("kullanici_adi") kullanici_adi: String) : CRUDCevap

}