package com.example.bitirmeprojesi.ui.viewmodel

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
class SepetViewModel @Inject constructor(val repo: YemeklerRepo): ViewModel() {

    var animFlag = false

    var sepettekiYemekler = MutableLiveData<List<SepetYemekler>>()

    fun sepettekileriGetir(kullanici_adi: String){
        viewModelScope.launch{
            try{
                sepettekiYemekler.value = repo.sepettekileriGetir(kullanici_adi)
            }
            catch(e: Exception){}
        }
    }

    fun sepettenYemekSil(sepetYemekID: String, kullanici_adi: String){
        viewModelScope.launch{
            try{
                repo.sepettenYemekSil(sepetYemekID, kullanici_adi)
                sepettekileriGetir("hross")
            }
            catch(e: Exception){}
        }
    }

}