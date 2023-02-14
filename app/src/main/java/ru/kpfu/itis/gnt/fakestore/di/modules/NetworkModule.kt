package ru.kpfu.itis.gnt.fakestore.di.modules


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.kpfu.itis.gnt.fakestore.data.services.ProductsService
import ru.kpfu.itis.gnt.fakestore.data.services.AuthService
import java.time.Duration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesProductsService(retrofit: Retrofit) : ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient() : OkHttpClient {
        val duration = Duration.ofSeconds(200)
        return OkHttpClient.Builder()
            .connectTimeout(duration)
            .readTimeout(duration)
            .writeTimeout(duration)
            .build()
    }
}
