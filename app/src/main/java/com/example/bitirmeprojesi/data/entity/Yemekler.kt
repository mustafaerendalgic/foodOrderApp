package com.example.bitirmeprojesi.data.entity

import kotlinx.serialization.Serializable

data class Yemekler(
    val yemek_id: Int,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int
) : java.io.Serializable {}
