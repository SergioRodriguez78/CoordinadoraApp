package com.coordinadora.coordinadoraapp.di

import android.app.Application
import com.coordinadora.coordinadoraapp.network.client.BaseApiClient
import com.coordinadora.coordinadoraapp.network.client.VolleyClient
import com.coordinadora.coordinadoraapp.onboarding.login.data.remote.AuthenticationService
import com.coordinadora.coordinadoraapp.onboarding.login.data.remote.AuthenticationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkClient(app: Application) : BaseApiClient {
        return VolleyClient(app)
    }

    @Provides
    @Singleton
    fun provideAuthService(client: BaseApiClient) : AuthenticationService {
        return AuthenticationServiceImpl(client)
    }
}
