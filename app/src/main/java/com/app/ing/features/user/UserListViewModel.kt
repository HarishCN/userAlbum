package com.app.ing.features.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ing.base.models.ResultOf
import com.app.ing.base.models.UserItem
import com.app.ing.features.profile.ProfileRepo
import com.bumptech.glide.load.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repository: ProfileRepo) :
    ViewModel() {
    private var _usersResponse: MutableLiveData<ResultOf<List<UserItem>>> = MutableLiveData()
    val usersResponse: MutableLiveData<ResultOf<List<UserItem>>>
        get() = _usersResponse

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _usersResponse.postValue(ResultOf.Loading)
            try {
                val response = repository.getAllUsers()
                _usersResponse.postValue(ResultOf.Success(response))
            } catch (ioe: IOException) {
                _usersResponse.postValue(ResultOf.Failure("[IO] error please retry", ioe))
            } catch (he: HttpException) {
                _usersResponse.postValue(ResultOf.Failure("[HTTP] error please retry", he))
            } catch (ex: Exception) {
                _usersResponse.postValue(
                    ResultOf.Failure(
                        "[exception] unknown error please retry",
                        ex
                    )
                )
            }
        }
    }
}