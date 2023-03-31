package com.app.ing.features.profile

import com.app.ing.base.models.UserAlbumItem
import com.app.ing.base.models.UserItem
import com.app.ing.base.network.Api
import javax.inject.Inject

class ProfileRepo @Inject constructor(private val api: Api) {

    suspend fun getAllUsers(): List<UserItem> {
        return api.getAllUsers()
    }

    suspend fun getUserAlbums(userId: Int): List<UserAlbumItem> {
        return api.getUserAlbums(userId)
    }
}