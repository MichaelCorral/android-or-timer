package com.michaelcorral.mvptemplate.api.models

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

data class ItunesContentResults(
    val trackId: Long? = 0L,
    val trackName: String? = "",
    val artworkUrl100: String? = "",
    val trackPrice: BigDecimal? = BigDecimal.ZERO,
    val primaryGenreName: String? = "",
    val longDescription: String? = "",
    val currency: String? = ""
) : Parcelable {

    val artworkUrl600: String?
        get() = artworkUrl100?.replace("100x100", "600x600")

    val artworkUrl200: String?
        get() = artworkUrl100?.replace("100x100", "200x200")

    constructor(source: Parcel) : this(
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readString(),
        source.readString(),
        source.readSerializable() as BigDecimal?,
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(trackId)
        writeString(trackName)
        writeString(artworkUrl100)
        writeSerializable(trackPrice)
        writeString(primaryGenreName)
        writeString(longDescription)
        writeString(currency)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ItunesContentResults> =
            object : Parcelable.Creator<ItunesContentResults> {
                override fun createFromParcel(source: Parcel): ItunesContentResults =
                    ItunesContentResults(source)

                override fun newArray(size: Int): Array<ItunesContentResults?> = arrayOfNulls(size)
            }
    }
}