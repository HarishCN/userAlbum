package com.app.ing.base.network

import com.app.ing.base.models.AlbumPhotoItem
import com.app.ing.base.models.UserAlbumItem
import com.app.ing.base.models.UserItem
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("users")
    suspend fun getAllUsers():List<UserItem>

    @GET("albums")
    suspend fun getUserAlbums(
        @Query("userId") userId: Int
    ): List<UserAlbumItem>

    @GET("photos")
    suspend fun getAlbumPhotos(
        @Query("albumId") albumId: Int
    ): List<AlbumPhotoItem>
}