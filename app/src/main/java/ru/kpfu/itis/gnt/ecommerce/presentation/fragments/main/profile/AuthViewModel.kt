package ru.kpfu.itis.gnt.ecommerce.presentation.fragments.main.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.kpfu.itis.gnt.ecommerce.data.mappers.UserMapper
import ru.kpfu.itis.gnt.ecommerce.data.models.LoginResponse
import ru.kpfu.itis.gnt.ecommerce.data.models.NetworkUser
import ru.kpfu.itis.gnt.ecommerce.data.repositories.AuthRepository
import ru.kpfu.itis.gnt.ecommerce.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.ecommerce.presentation.redux.Store
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
            val donUserResponse: Response<NetworkUser> = authRepository.fetchFourthUser()
            store.update { state ->
                state.copy(user = donUserResponse.body()?.let { networkUser ->
                    userMapper.build(networkUser)
                })
            }
        } else {
            Log.d("LOGIN", response.errorBody()?.toString()?:response.message())
        }

    }
}