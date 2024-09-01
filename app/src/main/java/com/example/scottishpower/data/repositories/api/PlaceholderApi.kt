package com.example.scottishpower.data.repositories.api

import com.example.scottishpower.data.dto.AlbumDTO
import com.example.scottishpower.data.dto.PhotoDTO
import com.example.scottishpower.data.dto.UserDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceholderApi {
    @GET("albums")
    fun getAllAlbums(): Call<List<AlbumDTO>>

    @GET("albums")
    fun getAlbumById(@Query("id") albumId: String): Call<List<AlbumDTO>>

    @GET("users")
    fun getUserById(@Query("id") userId: String): Call<List<UserDTO>>

    @GET("photos")
    fun getPhotosByAlbum(@Query("albumId") albumId: String): Call<List<PhotoDTO>>
}