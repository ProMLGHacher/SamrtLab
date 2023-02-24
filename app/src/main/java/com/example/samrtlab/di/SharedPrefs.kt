package com.example.samrtlab.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefs {

    @Provides
    @Singleton
    fun providesSharedPrefs(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences("my_app_context", Context.MODE_PRIVATE)
    }

}