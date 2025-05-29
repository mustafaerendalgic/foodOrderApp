package com.example.bitirmeprojesi.retrofit

class APIUtils {
    companion object{
        val baseURL = "http://kasimadalan.pe.hu/"
        fun getYemeklerDao() : YemekDao{
            return RetrofitClient.getClient(baseURL).create(YemekDao::class.java)
        }
    }
}