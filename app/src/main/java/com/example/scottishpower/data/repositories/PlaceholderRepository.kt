package com.example.scottishpower.data.repositories

import android.util.Log
import com.example.scottishpower.data.dto.AlbumDTO
import com.example.scottishpower.data.dto.PhotoDTO
import com.example.scottishpower.data.dto.UserDTO
import com.example.scottishpower.data.repositories.api.PlaceholderApi
import com.example.scottishpower.util.PlaceholderMissingDataException
import com.example.scottishpower.util.PlaceholderNetworkingException
import retrofit2.Call
import javax.inject.Inject

class PlaceholderRepository @Inject constructor(private val api: PlaceholderApi) {
    fun getAllAlbums(): List<AlbumDTO> {
        Log.d(TAG, "Fetching all albums")
        return executeCall(api.getAllAlbums())
    }

    fun getUserById(userId: String): List<UserDTO> {
        Log.d(TAG, "Fetching user $userId")
        return executeCall(api.getUserById(userId))
    }

    fun getAlbumPhotosById(albumId: String): List<PhotoDTO> {
        Log.d(TAG, "Fetching photos for album $albumId")
        return executeCall(api.getPhotosByAlbum(albumId))
    }

    fun getAlbumById(albumId: String): List<AlbumDTO> {
        Log.d(TAG, "Fetching album $albumId")
        return executeCall(api.getAlbumById(albumId))
    }

    private fun <T> executeCall(call: Call<out T>): T {
        val response = call.execute()

        if (!response.isSuccessful) {
            Log.w(TAG, "API call failed with HTTP status ${response.message()}")
            throw PlaceholderNetworkingException(response.message())
        }

        val body = response.body()

        if (body == null) {
            Log.w(TAG, "Response empty")
            throw PlaceholderMissingDataException()
        }

        return body
    }

    companion object {
        private val TAG = PlaceholderRepository::class.simpleName
    }
}