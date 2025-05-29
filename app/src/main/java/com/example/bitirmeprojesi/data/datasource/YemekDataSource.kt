package com.example.bitirmeprojesi.data.datasource

import android.util.Log
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.SepetYemeklerCevap
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.retrofit.YemekDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Field
import javax.inject.Inject

class YemekDataSource (val dao: YemekDao) {

    suspend fun yemekEkle(yemek_adi: String,
                          yemek_resim_adi: String,
                          yemek_fiyat: Int,
                          yemek_siparis_adet: Int,
                          kullanici_adi: String){

        val crudCevap = dao.sepeteYemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)

        Log.d("YemekEkleCevap", crudCevap.message)

    }

    suspend fun tumYemekleriGetir() : List<Yemekler> = withContext(Dispatchers.IO) {
        Log.e("DSLOG", "")
        return@withContext dao.tumYemekler().yemekler
    }

    suspend fun sepettekiYemekleriGetir(kullanici_adi: String): List<SepetYemekler>? = withContext(Dispatchers.IO) {
        try {
            val response = dao.sepettekiYemekleriGetir(kullanici_adi)
            response.sepet_yemekler
        } catch (e: Exception) {
            Log.e("API_ERROR", "Failed to get sepet items: ${e.message}", e)
            null
        }
    }

    suspend fun sepettenYemekSil(sepetYemekID: String, kullanici_adi: String){
        val crudCevap = dao.sepettenYemekSil(sepetYemekID, kullanici_adi)
        Log.d("YemekSilCevap", crudCevap.message)
    }

}