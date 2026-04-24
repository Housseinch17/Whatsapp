package com.example.whatsapp.app.di

import android.app.Application
import com.example.whatsapp.app.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideApplicationScope(app: Application): CoroutineScope {
        return (app as MyApp).applicationScope
    }
}