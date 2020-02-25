package com.michaelcorral.ortimer.di.modules

import androidx.room.Room
import com.michaelcorral.ortimer.data.TimeEntryRepository
import com.michaelcorral.ortimer.data.local.TimeEntryDatabase
import com.michaelcorral.ortimer.data.local.TimeEntryLocalDataSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single {
        Room.databaseBuilder(get(), TimeEntryDatabase::class.java, "ortimer_database").build()
    }

    single { get<TimeEntryDatabase>().timeEntryDao() }

    single(named("local")) { TimeEntryLocalDataSourceImpl(get()) }
    single {
        TimeEntryRepository(
            get(named("local"))
        )
    }
}