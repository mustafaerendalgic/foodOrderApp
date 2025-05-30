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

                val sepet = repo.sepettekileriGetir("hross")

                val item = sepet.find { it.yemek_adi == yemek.yemek_adi }

                if (item == null) {
                    sepeteEkle(yemek.yemek_adi, yemek.yemek_resim_adi, yemek.yemek_fiyat.toInt(), miktar, "hross")
                } else {
                    sepettenYemekSil(item.sepet_yemek_id, "hross")
                    val toplamMiktar = miktar + item.yemek_siparis_adet.toInt()
                    sepeteEkle(yemek.yemek_adi, yemek.yemek_resim_adi, yemek.yemek_fiyat.toInt(), toplamMiktar, "hross")
                }

                sepettekiYemekleriGetir()

            } catch (e: Exception) {

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