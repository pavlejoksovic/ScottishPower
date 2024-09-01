package com.example.scottishpower.domain

import com.example.scottishpower.data.entity.AlbumEntity
import com.example.scottishpower.data.repositories.PlaceholderRepository
import com.example.scottishpower.di.DispatcherModule.IoDispatcher
import com.example.scottishpower.util.PlaceholderMissingDataException
import com.example.scottishpower.util.firstOrThrow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

// this results in ~200 API calls and causes some overhead unfortunately
// I think this is the result of the API being slightly slow/throttled but this should be investigated
// potential improvement could be trickling in results rather than awaiting complete response
class GetAllAlbumsUseCase @Inject constructor(
    private val placeholderRepository: PlaceholderRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<AlbumEntity> = withContext(dispatcher) {
        placeholderRepository.getAllAlbums().map { album ->
            AlbumEntity(
                album.id,
                album.userId,
                album.title,
                placeholderRepository.getUserById(album.userId.toString()).firstOrThrow(
                    PlaceholderMissingDataException()
                ).username,
                placeholderRepository.getAlbumPhotosById(album.id.toString())
                    .firstOrThrow(PlaceholderMissingDataException()).thumbnailUrl
            )
        }
    }
}