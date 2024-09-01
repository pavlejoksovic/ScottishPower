package com.example.scottishpower.ui.albumlist

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.example.scottishpower.R
import com.example.scottishpower.data.entity.AlbumEntity
import com.example.scottishpower.ui.shared.ErrorMessage
import com.example.scottishpower.ui.shared.Spinner
import com.example.scottishpower.util.State
import com.example.scottishpower.util.TestTag

const val ALBUM_LIST_ROUTE = "album_list_route"

fun NavController.navigateToAlbumList() {
    navigate(ALBUM_LIST_ROUTE)
}

fun NavGraphBuilder.albumListScreen(navigateToDetail: (Int) -> Unit) {
    composable(ALBUM_LIST_ROUTE) {
        Box {
            val albumListViewModel: AlbumListViewModel = hiltViewModel()

            val albumListState by albumListViewModel.albumListState.collectAsState()
            val sortState by albumListViewModel.sortTypeState.collectAsState()

            when (albumListState) {
                is State.Error -> {
                    ErrorMessage(message = (albumListState as? State.Error)?.message)
                }

                is State.Loading -> {
                    Spinner()
                }

                is State.Success -> {
                    Column {
                        SortBar(sortState) {
                            albumListViewModel.setSortType(it)
                        }
                        AlbumList(
                            (albumListState as State.Success<List<AlbumEntity>>).contents,
                            navigateToDetail
                        )
                    }
                }
            }
        }
    }
}

@Composable
@VisibleForTesting
fun SortBar(selectedSort: SortType, sortCallback: (SortType) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.list_sort_by))
        FilterChip(
            onClick = { sortCallback.invoke(SortType.Title(selectedSort !is SortType.Title || !selectedSort.ascending)) },
            label = {
                Text(stringResource(R.string.sort_type_title))
            },
            selected = selectedSort is SortType.Title,
            modifier = Modifier.testTag(TestTag.TITLE_SORT_CHIP)
        )
        FilterChip(
            onClick = { sortCallback.invoke(SortType.Username(selectedSort !is SortType.Username || !selectedSort.ascending)) },
            label = {
                Text(stringResource(R.string.sort_type_username))
            },
            selected = selectedSort is SortType.Username,
            modifier = Modifier.testTag(TestTag.USERNAME_SORT_CHIP)
        )
    }
}

@Composable
private fun AlbumList(albums: List<AlbumEntity>, navigateToDetail: (Int) -> Unit) {
    LazyColumn(Modifier.testTag(TestTag.ALBUM_LIST_CONTAINER)) {
        items(albums, itemContent = { AlbumItem(album = it, navigateToDetail) })
    }
}

@Composable
private fun AlbumItem(album: AlbumEntity, navigateToDetail: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(96.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = { navigateToDetail.invoke(album.albumId) }
    ) {
        Row {
            AsyncImage(
                model = album.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(),
                placeholder = painterResource(R.drawable.thumbnail_placeholder),
                error = painterResource(R.drawable.thumbnail_error)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.detail_user, album.username),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}