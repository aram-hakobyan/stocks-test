package com.nativeteams.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nativeteams.common.data.network.HeaderInterceptor
import com.nativeteams.common.data.network.StocksApi
import com.nativeteams.common.data.datasource.StocksLocalDataSource
import com.nativeteams.common.data.datasource.StocksRemoteDataSource
import com.nativeteams.common.data.repository.StocksRepositoryImpl
import com.nativeteams.common.data.repository.TokenRepositoryImpl
import com.nativeteams.common.domain.repository.StocksRepository
import com.nativeteams.common.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    private const val API_HOST = "yh-finance.p.rapidapi.com"
    private const val TIMEOUT_SEC = 60L

    @Provides
    @Singleton
    fun tokenRepository(): TokenRepository {
        return TokenRepositoryImpl()
    }

    @Provides
    @Singleton
    fun stocksRepository(
        stocksRemoteDataSource: StocksRemoteDataSource,
        stocksLocalDataSource: StocksLocalDataSource
    ): StocksRepository {
        return StocksRepositoryImpl(
            stocksRemoteDataSource,
            stocksLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun stocksRemoteDataSource(
        stocksApi: StocksApi
    ): StocksRemoteDataSource {
        return StocksRemoteDataSource(
            api = stocksApi,
            dispatcher = Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun stocksLocalDataSource(): StocksLocalDataSource {
        return StocksLocalDataSource()
    }

    @Provides
    @Singleton
    fun stocksApi(
        gson: Gson,
        repository: TokenRepository
    ): StocksApi {
        return Retrofit.Builder()
            .baseUrl("https://$API_HOST")
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .client(
                okHttpClient(repository)
            )
            .build()
            .create(StocksApi::class.java)
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    private fun okHttpClient(
        tokenRepository: TokenRepository
    ): OkHttpClient {
        val headerInterceptor = HeaderInterceptor(
            API_HOST, tokenRepository
        )

        return OkHttpClient()
            .newBuilder()
            .addInterceptor(headerInterceptor)
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()
    }
}