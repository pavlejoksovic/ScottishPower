package com.example.scottishpower.di

import androidx.lifecycle.SavedStateHandle
import com.example.scottishpower.util.NavArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object NavArgsModule {

    @Provides
    @AlbumId
    fun provideAlbumId(savedStateHandle: SavedStateHandle): Int = checkNotNull(savedStateHandle[NavArgs.ALBUM_ID])

    annotation class AlbumId
}