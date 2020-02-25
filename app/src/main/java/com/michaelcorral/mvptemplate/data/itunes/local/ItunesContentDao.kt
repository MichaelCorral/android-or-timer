package com.michaelcorral.mvptemplate.data.itunes.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.michaelcorral.mvptemplate.data.itunes.local.ItunesContent
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ItunesContentDao {

    @Insert
    fun insertItunesContent(itunesContent: ItunesContent): Single<Long>

    @Query("SELECT * FROM ItunesContent")
    fun queryItunesContent(): Single<ItunesContent>

    @Query("DELETE FROM ItunesContent")
    fun deleteAllItunesContent(): Completable
}