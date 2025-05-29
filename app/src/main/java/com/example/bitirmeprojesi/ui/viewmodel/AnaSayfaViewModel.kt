package com.example.bitirmeprojesi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.repo.YemeklerRepo
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnaSayfaViewModel @Inject constructor(val repo : YemeklerRepo) : ViewModel() {

    var yemekListesi = MutableLiveData<List<Yemekler>>()

    fun tumYemekleriGetir(){
        try {
            viewModelScope.launch {
                yemekListesi.value = repo.tumYemekleriGetir()
            }
        }
        catch (e: Exception){
            Log.e("yemekleriGetir", "Basarisiz")
        }
    }

    fun sepettenYemekSil(sepetYemekID: String, kullaniciAdi: String){
        try {
            viewModelScope.launch {
                repo.sepettenYemekSil(sepetYemekID, kullaniciAdi)
                repo.sepettekileriGetir(kullaniciAdi)
            }
        }
        catch (e: Exception){}
    }

    fun sepeteYemekEkle(yemek_adi: String,
                        yemek_resim_adi: String,
                        yemek_fiyat: Int,
                        yemek_siparis_adet: Int,
                        kullanici_adi: String){
        try {
            viewModelScope.launch {
                repo.yemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
            }
        }
        catch (e: Exception){}
    }

}