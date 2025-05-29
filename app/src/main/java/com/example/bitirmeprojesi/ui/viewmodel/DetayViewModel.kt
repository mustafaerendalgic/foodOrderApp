package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.repo.YemeklerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor(val repo: YemeklerRepo): ViewModel() {

    var yemekListesi = MutableLiveData<List<Yemekler>>()
    var sepetYemekListesi = MutableLiveData<List<SepetYemekler>>()
    var itemFlag = false

    init {
        sepettekiYemekleriGetir()
    }


    fun sepeteEkle(yemek_adi: String,
                   yemek_resim_adi: String,
                   yemek_fiyat: Int,
                   yemek_siparis_adet: Int,
                   kullanici_adi: String){
        viewModelScope.launch{
            try {
                repo.yemekEkle(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    yemek_siparis_adet,
                    kullanici_adi
                )
            }
            catch(e: Exception){}
        }
    }

    fun sepeteEkleSartli(yemek: Yemekler, miktar: Int) {
        viewModelScope.launch {
            try {
                Log.d("SEPET_DEBUG", "sepeteEkleSartli called with ${yemek.yemek_adi}")

                // 🔥 Fetch fresh data directly from repo
                val sepet = repo.sepettekileriGetir("hross")
                Log.d("SEPET_DEBUG", "Existing items: ${sepet.map { it.yemek_adi }}")

                val item = sepet.find { it.yemek_adi == yemek.yemek_adi }

                if (item == null) {
                    Log.d("SEPET_DEBUG", "Item not in cart, adding new: ${yemek.yemek_adi}")
                    sepeteEkle(yemek.yemek_adi, yemek.yemek_resim_adi, yemek.yemek_fiyat.toInt(), miktar, "hross")
                } else {
                    Log.d("SEPET_DEBUG", "Item already in cart, updating: ${yemek.yemek_adi}")
                    sepettenYemekSil(item.sepet_yemek_id, "hross")
                    val toplamMiktar = miktar + item.yemek_siparis_adet.toInt()
                    sepeteEkle(yemek.yemek_adi, yemek.yemek_resim_adi, yemek.yemek_fiyat.toInt(), toplamMiktar, "hross")
                }

                sepettekiYemekleriGetir() // Refresh UI LiveData

            } catch (e: Exception) {
                Log.e("SEPET_DEBUG", "Exception in sepeteEkleSartli: ${e.localizedMessage}", e)
            }
        }
    }

    fun tumYemekleriGetir(){
        viewModelScope.launch{
            try {
                yemekListesi.value = repo.tumYemekleriGetir()
            }
            catch(e: Exception){}
        }
    }

    fun sepettekiYemekleriGetir(){
        viewModelScope.launch{
            try {
                sepetYemekListesi.value = repo.sepettekileriGetir("hross")
            }
            catch(e: Exception){}
        }
    }

    fun sepettenYemekSil(id: String, kullanici_adi: String){
        viewModelScope.launch{
            try {
                repo.sepettenYemekSil(id, kullanici_adi)
            }
            catch(e: Exception){}
        }
    }

}