package com.michaelcorral.mvptemplate.di.modules

import com.michaelcorral.mvptemplate.BuildConfig
import com.michaelcorral.mvptemplate.api.services.ItunesService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT: Long = 20
private const val READ_TIMEOUT: Long = 20

val networkModule = module {
    single { provideItunesService(get()) }
    single { provideRetrofit(get(), get(), get()) }
    single { provideOkHttpClient() }
    single { provideGsonConverterFactory() }
    single { provideRxJava2CallAdapterFactory() }
}

private fun provideItunesService(retrofit: Retrofit): ItunesService = retrofit.create(ItunesService::class.java)

private fun provideRetrofit(
    client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
    adapterFactory: RxJava2CallAdapterFactory): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.itunes_base_api)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(adapterFactory)
        .client(client)
        .build()

private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .build()

private fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

private fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())