package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.repo.YemeklerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriViewModel @Inject constructor(val repo: YemeklerRepo) : ViewModel(){

    var favoriList = MutableLiveData<List<Yemekler>>()
    var list = mutableMapOf<String, Yemekler>()

    fun favorilereEkle(yemek: Yemekler, adi: String){
        if(list[adi] == null)
            list[adi] = yemek
    }

    fun favorilerdenSil(ad: String){
        list.remove(ad)
    }

}