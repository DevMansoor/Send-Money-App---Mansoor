package com.example.sendmoney_mansoor.di

import android.content.Context
import com.example.sendmoney_mansoor.data.repository.SendMoneyRepository
import com.example.sendmoney_mansoor.util.LanguageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLanguageManager(@ApplicationContext context: Context): LanguageManager = LanguageManager(context)

    @Provides
    @Singleton
    fun provideRepository(@ApplicationContext context: Context): SendMoneyRepository = SendMoneyRepository(context)
}