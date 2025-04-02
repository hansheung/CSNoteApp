package com.hansheung.mob22_mvi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("msg1")
    fun provideGreetingMsg1(): String{
        return "Hello Dagger Hilt 2 1"
    }

}