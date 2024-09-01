package com.example.scottishpower.ui.albumlist

import android.app.Application
import android.util.Log
import com.example.scottishpower.data.entity.AlbumEntity
import com.example.scottishpower.domain.GetAllAlbumsUseCase
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
class AlbumListViewModelTest {

    private lateinit var viewModel: AlbumListViewModel
    private lateinit var application: Application
    private lateinit var getAllAlbums: GetAllAlbumsUseCase

    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        application = mockk<Application>()
        getAllAlbums = mockk<GetAllAlbumsUseCase>()

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenApiRespondsSuccessfully_whenViewModelInits_thenAlbumsSavedInDefaultSortOrder() = runTest {
        coEvery { getAllAlbums.invoke() } returns albumsUnsorted

        viewModel = AlbumListViewModel(application, getAllAlbums)

        advanceUntilIdle()

        assert(viewModel.albumListState.value is State.Success)
        assert((viewModel.albumListState.value as State.Success).contents == albumsSortedByTitleAscending)
    }

    @Test
    fun givenApiRespondsSuccessfully_whenViewModelInits_andUserChangedSortType_thenAlbumsSavedInNewSortOrder() = runTest {
        coEvery { getAllAlbums.invoke() } returns albumsUnsorted

        viewModel = AlbumListViewModel(application, getAllAlbums)

        advanceUntilIdle()

        assert(viewModel.albumListState.value is State.Success)
        assert((viewModel.albumListState.value as State.Success).contents == albumsSortedByTitleAscending)

        viewModel.setSortType(SortType.Username(false))

        advanceUntilIdle()

        assert(viewModel.albumListState.value is State.Success)
        assert((viewModel.albumListState.value as State.Success).contents == albumsSortedByUsernameDescending)
    }

    @Test
    fun givenUserChangesSortTypeBeforeViewModelInits_andApiRespondsSuccessfully_thenAlbumsSavedInNewSortOrder() = runTest {
        coEvery { getAllAlbums.invoke() } returns albumsUnsorted

        viewModel = AlbumListViewModel(application, getAllAlbums)

        viewModel.setSortType(SortType.Username(true))

        advanceUntilIdle()

        assert(viewModel.albumListState.value is State.Success)
        assert((viewModel.albumListState.value as State.Success).contents == albumsSortedByUsernameAscending)
    }

    @Test
    fun givenApiThrowsError_whenViewModelInits_thenErrorStateStored() = runTest {
        val error = "error"
        coEvery { getAllAlbums.invoke() } throws Exception(error)

        viewModel = AlbumListViewModel(application, getAllAlbums)

        advanceUntilIdle()

        assert(viewModel.albumListState.value is State.Error)
        assert((viewModel.albumListState.value as State.Error).message == error)
    }


    private val firstAlbum = AlbumEntity(1, 1, "a", "z", "")
    private val secondAlbum = AlbumEntity(1, 1, "b", "y", "")
    private val thirdAlbum = AlbumEntity(1, 1, "c", "x", "")

    private val albumsUnsorted = listOf(secondAlbum, firstAlbum, thirdAlbum)
    private val albumsSortedByTitleAscending = listOf(firstAlbum, secondAlbum, thirdAlbum)
    private val albumsSortedByTitleDescending = albumsSortedByTitleAscending.reversed()
    private val albumsSortedByUsernameAscending = listOf(thirdAlbum, secondAlbum, firstAlbum)
    private val albumsSortedByUsernameDescending = albumsSortedByUsernameAscending.reversed()
}