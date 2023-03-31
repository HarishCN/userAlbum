package com.app.ing.features.albumDetails

import com.app.ing.base.models.AlbumPhotoItem
import com.app.ing.base.network.Api
import javax.inject.Inject

class AlbumDetailsRepo @Inject constructor(private val api: Api) {

    suspend fun getAlbumPhotos(albumId: Int): List<AlbumPhotoItem> {
        return api.getAlbumPhotos(albumId)
    }
}