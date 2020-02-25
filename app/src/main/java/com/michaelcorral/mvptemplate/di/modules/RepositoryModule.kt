package com.michaelcorral.mvptemplate.di.modules

import androidx.room.Room
import com.michaelcorral.mvptemplate.data.itunes.ItunesRepository
import com.michaelcorral.mvptemplate.data.itunes.local.ItunesDatabase
import com.michaelcorral.mvptemplate.data.itunes.local.ItunesLocalDataSourceImpl
import com.michaelcorral.mvptemplate.data.itunes.remote.ItunesRemoteDataSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single {
        Room.databaseBuilder(get(), ItunesDatabase::class.java, "itunes_database").build()
    }

    single { get<ItunesDatabase>().itunesContentDao()}

    single(named("local")) { ItunesLocalDataSourceImpl(get()) }
    single(named("remote")) { ItunesRemoteDataSourceImpl(get()) }
    single {
        ItunesRepository(
            get(named("local")),
            get(named("remote"))
        )
    }
}