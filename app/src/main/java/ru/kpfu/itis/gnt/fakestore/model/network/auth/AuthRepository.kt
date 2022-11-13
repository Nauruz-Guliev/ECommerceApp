package ru.kpfu.itis.gnt.fakestore.model.network.auth

import retrofit2.Response
import ru.kpfu.itis.gnt.fakestore.model.network.LoginResponse
import ru.kpfu.itis.gnt.fakestore.model.network.NetworkUser
import ru.kpfu.itis.gnt.fakestore.model.network.post.LoginPostBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
){
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return authService.login(LoginPostBody(
            username, password
        ))
    }
}