package com.example.scottishpower.ui.albumlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.scottishpower.data.entity.AlbumEntity
import com.example.scottishpower.domain.GetAllAlbumsUseCase
import com.example.scottishpower.util.State
import com.example.scottishpower.util.sortedBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    application: Application,
    private val getAllAlbums: GetAllAlbumsUseCase
) : AndroidViewModel(application) {

    private val _albumListState = MutableStateFlow<State<List<AlbumEntity>>>(State.Loading)
    val albumListState: StateFlow<State<List<AlbumEntity>>> = _albumListState.asStateFlow()

    private val _sortTypeState = MutableStateFlow<SortType>(SortType.Title(ascending = true))
    val sortTypeState: StateFlow<SortType> = _sortTypeState.asStateFlow()

    init {
        retrieveAlbums()
    }

    fun setSortType(sortType: SortType) {
        _sortTypeState.value = sortType

        // if albums are already downloaded, this will sort them, otherwise just set type and await
        (albumListState.value as? State.Success<List<AlbumEntity>>)?.contents?.let { albums ->
            _albumListState.value = State.Success(sortAlbums(albums, sortType))
        }
    }

    private fun retrieveAlbums() {
        viewModelScope.launch {
            try {
                _albumListState.value = State.Success(sortAlbums(getAllAlbums.invoke(), sortTypeState.value))
            } catch (exception: Exception) {
                _albumListState.value = State.Error(exception.message)
            }
        }
    }

    private fun sortAlbums(albums: List<AlbumEntity>, sortType: SortType): List<AlbumEntity> {
        return when(sortType){
            is SortType.Title -> albums.sortedBy(sortType.ascending) { it.title }
            is SortType.Username -> albums.sortedBy(sortType.ascending) { it.username }
        }
    }

}