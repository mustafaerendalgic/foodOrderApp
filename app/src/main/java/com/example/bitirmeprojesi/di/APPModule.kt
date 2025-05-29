package com.example.bitirmeprojesi.di

import com.example.bitirmeprojesi.data.datasource.YemekDataSource
import com.example.bitirmeprojesi.data.repo.YemeklerRepo
import com.example.bitirmeprojesi.retrofit.APIUtils
import com.example.bitirmeprojesi.retrofit.YemekDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APPModule {

    @Provides
    @Singleton
    fun provideDao() : YemekDao{
        return APIUtils.getYemeklerDao()
    }

    @Provides
    @Singleton
    fun provideDataSource(dao: YemekDao) : YemekDataSource{
        return YemekDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideRepo(ds: YemekDataSource) : YemeklerRepo{
        return YemeklerRepo(ds)
    }

}