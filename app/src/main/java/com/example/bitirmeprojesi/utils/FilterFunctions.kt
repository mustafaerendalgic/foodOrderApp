package com.example.bitirmeprojesi.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.ui.adapter.YemekAdapter

fun ListeFiltrele(tur: String, adapter: YemekAdapter, ctx: Context, it: List<Yemekler>) {
    var yemekList = listOf<Yemekler>()
    when(tur){
        ContextCompat.getString(ctx, R.string.anaYemek) -> yemekList = it.filter { isim -> yemekIsimleri.any { it.equals(isim.yemek_adi) }}
        ContextCompat.getString(ctx, R.string.tatli) -> yemekList = it.filter { isim -> tatliIsimleri.any { it.equals(isim.yemek_adi) }}
        ContextCompat.getString(ctx, R.string.icecek) -> yemekList = it.filter { isim -> icecekIsimleri.any { it.equals(isim.yemek_adi) }}
        ContextCompat.getString(ctx, R.string.tumu) -> yemekList = it
        else -> yemekList = it
    }
    adapter.updateList(yemekList)
}

