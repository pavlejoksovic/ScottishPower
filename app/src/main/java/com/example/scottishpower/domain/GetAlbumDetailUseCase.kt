package com.example.scottishpower.domain

import com.example.scottishpower.data.entity.AlbumDetailEntity
import com.example.scottishpower.data.repositories.PlaceholderRepository
import com.example.scottishpower.di.DispatcherModule.IoDispatcher
import com.example.scottishpower.util.PlaceholderMissingDataException
import com.example.scottishpower.util.firstOrThrow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAlbumDetailUseCase @Inject constructor(
    private val placeholderRepository: PlaceholderRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(albumId: Int): AlbumDetailEntity = withContext(dispatcher) {
        val album = placeholderRepository.getAlbumById(albumId.toString()).firstOrThrow(PlaceholderMissingDataException())
        val user = placeholderRepository.getUserById(album.userId.toString()).firstOrThrow(PlaceholderMissingDataException())
        val photos = placeholderRepository.getAlbumPhotosById(albumId.toString())

        AlbumDetailEntity(
            albumId,
            album.title,
            user.username,
            user.name,
            user.company.name,
            user.company.catchPhrase,
            photos.map { it.url })
    }
}