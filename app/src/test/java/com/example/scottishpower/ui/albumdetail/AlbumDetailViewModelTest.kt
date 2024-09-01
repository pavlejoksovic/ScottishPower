package com.example.scottishpower.ui.albumdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.example.scottishpower.data.entity.AlbumDetailEntity
import com.example.scottishpower.domain.GetAlbumDetailUseCase
import com.example.scottishpower.util.State
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumDetailViewModelTest {

    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var application: Application
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getAlbumDetail: GetAlbumDetailUseCase

    private val dispatcher = StandardTestDispatcher()
    private val albumId = 1

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        application = mockk<Application>()
        savedStateHandle = mockk<SavedStateHandle>()
        getAlbumDetail = mockk<GetAlbumDetailUseCase>()

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenApiRespondsSuccessfully_whenViewModelInits_thenDetailsSaved() = runTest {
        val details = AlbumDetailEntity.empty()
        coEvery { getAlbumDetail.invoke(albumId) } returns details
        viewModel = AlbumDetailViewModel(application, getAlbumDetail, albumId)

        advanceUntilIdle()

        assert(viewModel.albumDetailState.value is State.Success)
        assert((viewModel.albumDetailState.value as State.Success).contents == details)
    }

    @Test
    fun givenApiRespondsUnsuccessfully_whenViewModelInits_thenErrorSaved() = runTest {
        val error = "error"
        coEvery { getAlbumDetail.invoke(albumId) } throws Exception(error)
        viewModel = AlbumDetailViewModel(application, getAlbumDetail, albumId)

        advanceUntilIdle()

        assert(viewModel.albumDetailState.value is State.Error)
        assert((viewModel.albumDetailState.value as State.Error).message == error)
    }
}