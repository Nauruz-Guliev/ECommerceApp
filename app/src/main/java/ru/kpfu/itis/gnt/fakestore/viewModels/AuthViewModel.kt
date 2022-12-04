package ru.kpfu.itis.gnt.fakestore.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.kpfu.itis.gnt.fakestore.model.mapper.UserMapper
import ru.kpfu.itis.gnt.fakestore.model.network.LoginResponse
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkUser
import ru.kpfu.itis.gnt.fakestore.model.network.auth.AuthRepository
import ru.kpfu.itis.gnt.fakestore.model.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.redux.Store
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val authRepository: AuthRepository,
    private val userMapper: UserMapper
) : ViewModel() {

    //can be called from non-suspending function
    fun login(userName: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(userName, password)

        if (response.isSuccessful) {
            val donUserResponse: Response<NetworkUser> = authRepository.fetchDon()
            store.update { it ->
                it.copy(user = donUserResponse.body()?.let {
                    userMapper.build(it)
                })
            }
        } else {
            Log.d("LOGIN", response.errorBody()?.toString()?:response.message())
        }

    }
}