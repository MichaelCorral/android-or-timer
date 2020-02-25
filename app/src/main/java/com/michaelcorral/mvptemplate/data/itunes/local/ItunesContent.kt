package com.michaelcorral.mvptemplate.data.itunes.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michaelcorral.mvptemplate.api.models.ItunesContentResults

@Entity(tableName = "ItunesContent")
data class ItunesContent(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "trackName")
    val trackName: String,

    @ColumnInfo(name = "artworkUrl100")
    val artworkUrl100: String,

    @ColumnInfo(name = "trackPrice")
    val trackPrice: String,

    @ColumnInfo(name = "primaryGenreName")
    val primaryGenreName: String,

    @ColumnInfo(name = "longDescription")
    val longDescription: String,

    @ColumnInfo(name = "currency")
    val currency: String
) {
    fun toItunesContentResults(content: ItunesContent): ItunesContentResults {
        return ItunesContentResults(
            trackId = content.id,
            trackName = content.trackName,
            artworkUrl100 = content.artworkUrl100,
            trackPrice = content.trackPrice.toBigDecimal(),
            currency = content.currency,
            longDescription = content.longDescription,
            primaryGenreName = content.primaryGenreName
        )
    }
}