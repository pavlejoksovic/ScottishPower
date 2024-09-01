package com.example.scottishpower.ui.albumdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.scottishpower.data.entity.AlbumDetailEntity
import com.example.scottishpower.di.NavArgsModule
import com.example.scottishpower.di.NavArgsModule.AlbumId
import com.example.scottishpower.domain.GetAlbumDetailUseCase
import com.example.scottishpower.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    application: Application,
    private val getAlbumDetail: GetAlbumDetailUseCase,
    @AlbumId private val albumId: Int
) : AndroidViewModel(application) {
    private val _albumDetailState = MutableStateFlow<State<AlbumDetailEntity>>(State.Loading)
    val albumDetailState: StateFlow<State<AlbumDetailEntity>> = _albumDetailState.asStateFlow()

    init {
        fetchAlbumDetails()
    }

    private fun fetchAlbumDetails() {
        viewModelScope.launch {
            try {
                _albumDetailState.value = State.Success(getAlbumDetail.invoke(albumId))
            } catch (exception: Exception) {
                _albumDetailState.value = State.Error(exception.message)
            }
        }
    }

}