package com.thirtyeight.thirtyeight.data.di

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.thirtyeight.thirtyeight.BuildConfig
import com.thirtyeight.thirtyeight.data.datasource.DefaultMechanicsDataSource
import com.thirtyeight.thirtyeight.data.datasource.MechanicsDataSource
import com.thirtyeight.thirtyeight.data.datasource.remote.AuthRemoteDataSource
import com.thirtyeight.thirtyeight.data.remote.ServiceApi
import com.thirtyeight.thirtyeight.data.remote.datasource.DefaultAuthRemoteDataSource
import com.thirtyeight.thirtyeight.data.repository.DefaultAuthRepository
import com.thirtyeight.thirtyeight.data.repository.DefaultMechanicsRepository
import com.thirtyeight.thirtyeight.domain.repository.AuthRepository
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */

val repositoryModule = module {
    single<MechanicsRepository> { DefaultMechanicsRepository(get()) }
    single<AuthRepository> { DefaultAuthRepository(get()) }
}

val dataSourceModule = module {
    single<MechanicsDataSource> { DefaultMechanicsDataSource() }
    single<AuthRemoteDataSource> { DefaultAuthRemoteDataSource(get()) }
}

val networkModule = module {

    factory { provideOkHttpClient(get()) }
    factory { provideServiceApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient {
    return OkHttpClient().newBuilder().apply {
        addNetworkInterceptor(loggingInterceptor)
    }.build()
}

fun provideLoggingInterceptor(): Interceptor {
    return LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("HttpLogRequest")
            .response("HttpLogResponse")
            .addHeader("Version", BuildConfig.VERSION_NAME)
            .build()
}

fun provideServiceApi(retrofit: Retrofit): ServiceApi = retrofit.create(ServiceApi::class.java)