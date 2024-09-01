package com.example.scottishpower.ui.albumdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.example.scottishpower.R
import com.example.scottishpower.data.entity.AlbumDetailEntity
import com.example.scottishpower.ui.shared.ErrorMessage
import com.example.scottishpower.ui.shared.Spinner
import com.example.scottishpower.util.NavArgs
import com.example.scottishpower.util.State
import com.example.scottishpower.util.navigateWithArgs

const val ALBUM_DETAIL_ROUTE = "album_detail_route"

fun NavController.navigateToAlbumDetail(albumId: Int) {
    navigateWithArgs(ALBUM_DETAIL_ROUTE, bundleOf(NavArgs.ALBUM_ID to albumId))
}

fun NavGraphBuilder.albumDetailScreen() {
    composable(ALBUM_DETAIL_ROUTE) {

        Box(modifier = Modifier
            .padding(8.dp, 8.dp)
            .fillMaxWidth()) {
            val albumDetailViewModel: AlbumDetailViewModel = hiltViewModel()

            val albumDetailState by albumDetailViewModel.albumDetailState.collectAsState()

            when (albumDetailState) {
                is State.Error -> {
                    ErrorMessage(message = (albumDetailState as? State.Error)?.message)
                }
                is State.Loading -> {
                    Spinner()
                }
                is State.Success -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        (albumDetailState as? State.Success<AlbumDetailEntity>)?.contents?.let { detail ->
                            AlbumDetailsHeader(
                                title = detail.title,
                                username = detail.username,
                                name = detail.name,
                                companyName = detail.companyName,
                                companyCatchPhrase = detail.companyCatchPhrase
                            )
                            Spacer(modifier = Modifier.height(8.dp) )
                            AlbumGallery(photoUrls = detail.photoUrls)
                            Spacer(modifier = Modifier.height(16.dp) )
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumDetailsHeader(
    title: String,
    username: String,
    name: String,
    companyName: String,
    companyCatchPhrase: String
) {
    Text(text = title, textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth())
    Text(text = stringResource(id = R.string.detail_user, username), style = MaterialTheme.typography.bodyMedium)
    Text(text = stringResource(id = R.string.detail_name, name), style = MaterialTheme.typography.bodyMedium)
    Text(text = stringResource(id = R.string.detail_company, companyName), style = MaterialTheme.typography.bodySmall)
    Text(text = companyCatchPhrase, style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun AlbumGallery(photoUrls: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(photoUrls, itemContent = { AlbumGalleryItem(photoUrl = it) })
    }
}

@Composable
private fun AlbumGalleryItem(photoUrl: String) {
    AsyncImage(model = photoUrl, contentDescription = null)
}