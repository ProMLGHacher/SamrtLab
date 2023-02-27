package com.example.samrtlab.di

import android.content.SharedPreferences
import com.example.samrtlab.data.repository.PasswordRepositoryImpl
import com.example.samrtlab.data.repository.TokenRepositoryImpl
import com.example.samrtlab.data.repository.UserReposImpl
import com.example.samrtlab.domain.repository.PasswordRepository
import com.example.samrtlab.domain.repository.TokenRepository
import com.example.samrtlab.domain.repository.UserRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        sharedPreferences: SharedPreferences
    ): UserRepos {
        return UserReposImpl(
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun provideTokenRepository(
        sharedPreferences: SharedPreferences
    ): TokenRepository {
        return TokenRepositoryImpl(
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun providePasswordRepository(
        sharedPreferences: SharedPreferences
    ): PasswordRepository {
        return PasswordRepositoryImpl(
            sharedPreferences = sharedPreferences
        )
    }

}