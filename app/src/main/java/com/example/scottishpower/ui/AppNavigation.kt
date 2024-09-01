package com.example.scottishpower.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.scottishpower.MainActivity
import com.example.scottishpower.ui.albumdetail.albumDetailScreen
import com.example.scottishpower.ui.albumdetail.navigateToAlbumDetail
import com.example.scottishpower.ui.albumlist.ALBUM_LIST_ROUTE
import com.example.scottishpower.ui.albumlist.albumListScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = ALBUM_LIST_ROUTE,
        ) {
            albumListScreen { navController.navigateToAlbumDetail(it) }
            albumDetailScreen()
        }
    }
}