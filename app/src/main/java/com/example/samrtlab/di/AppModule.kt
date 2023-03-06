package com.example.samrtlab.di

import android.content.SharedPreferences
import com.example.samrtlab.data.repository.*
import com.example.samrtlab.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        httpClient: HttpClient
    ): NewsRepository {
        return NewsRepositoryImpl(
            httpClient = httpClient
        )
    }

    @Provides
    @Singleton
    fun provideCartRepository(): CartRepository {
        return CartRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        sharedPreferences: SharedPreferences,
        httpClient: HttpClient
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(
        httpClient: HttpClient
    ): CatalogRepository {
        return CatalogRepositoryImpl(
            httpClient = httpClient
        )
    }

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