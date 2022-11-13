package ru.kpfu.itis.gnt.fakestore.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.kpfu.itis.gnt.fakestore.model.network.LoginResponse
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkUser
import ru.kpfu.itis.gnt.fakestore.model.network.auth.AuthRepository
import ru.kpfu.itis.gnt.fakestore.model.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.redux.Store
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val authRepository: AuthRepository
) : ViewModel() {

    //can be called from non-suspending function
    fun login(userName: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(userName, password)

        if (response.isSuccessful) {
            store.update {
                it.copy(user = NetworkUser())
            }
        } else {

        }

    }
}