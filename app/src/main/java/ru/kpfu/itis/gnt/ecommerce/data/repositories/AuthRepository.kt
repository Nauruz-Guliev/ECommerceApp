package ru.kpfu.itis.gnt.ecommerce.data.repositories

import retrofit2.Response
import ru.kpfu.itis.gnt.ecommerce.data.services.AuthService
import ru.kpfu.itis.gnt.ecommerce.data.models.LoginResponse
import ru.kpfu.itis.gnt.ecommerce.data.models.NetworkUser
import ru.kpfu.itis.gnt.ecommerce.data.models.post.LoginPostBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
){
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return authService.login(
            LoginPostBody(
            username, password
        )
        )
    }

    /*
    temporary solution
     */
    suspend fun fetchFourthUser(): Response<NetworkUser> {
        return authService.fetchUser(4)
    }
}