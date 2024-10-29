package com.coordinadora.coordinadoraapp.di

import android.app.Application
import com.coordinadora.coordinadoraapp.network.client.BaseApiClient
import com.coordinadora.coordinadoraapp.network.client.VolleyClient
import com.coordinadora.coordinadoraapp.onboarding.home.data.ImageService
import com.coordinadora.coordinadoraapp.onboarding.home.data.ImageServiceImpl
import com.coordinadora.coordinadoraapp.onboarding.login.data.remote.AuthenticationService
import com.coordinadora.coordinadoraapp.onboarding.login.data.remote.AuthenticationServiceImpl
import com.coordinadora.coordinadoraapp.service.pdf.PdfServiceManager
import com.coordinadora.coordinadoraapp.service.pdf.PdfServiceManagerImpl
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

    @Provides
    @Singleton
    fun provideImageService(client: BaseApiClient) : ImageService {
        return ImageServiceImpl(client)
    }

    @Provides
    @Singleton
    fun providePdfManager(context: Application) : PdfServiceManager {
        return PdfServiceManagerImpl(context)
    }
}
