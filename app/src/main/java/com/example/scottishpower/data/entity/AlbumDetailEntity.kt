package com.example.scottishpower.data.entity

data class AlbumDetailEntity(
    val albumId: Int,
    val title: String,
    val username: String,
    val name: String,
    val companyName: String,
    val companyCatchPhrase: String,
    val photoUrls: List<String>
) {
    companion object {
        fun empty() = AlbumDetailEntity(-1, "", "", "", "", "", emptyList())
    }
}