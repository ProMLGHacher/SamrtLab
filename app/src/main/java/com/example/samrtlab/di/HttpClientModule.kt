package com.example.samrtlab.di

import android.util.Log
import androidx.compose.animation.slideIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun provideHttpClient() : HttpClient {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            expectSuccess
            defaultRequest {
                url("https://medic.madskill.ru")
            }
        }
        client.plugin(HttpSend).intercept { request ->
            val originalCall = execute(request)
            Log.e("aa", request.url.toString())
            originalCall
        }
        return client
    }

}