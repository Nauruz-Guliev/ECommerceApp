package ru.kpfu.itis.gnt.fakestore.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.gnt.fakestore.presentation.models.states.ApplicationState
import ru.kpfu.itis.gnt.fakestore.presentation.redux.Store
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStateModule {

    @Provides
    @Singleton
    fun providesApplicationStateStore(): Store<ApplicationState> {
        return Store(ApplicationState())
    }
}
