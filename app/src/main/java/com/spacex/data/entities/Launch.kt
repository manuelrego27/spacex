package com.spacex.data.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "launch_table")
@Parcelize
data class Launch(
    @PrimaryKey
    val flight_number: Int,
    val launch_year: Int,
    val mission_name: String,
    val launch_date_utc: String,
    val launch_success: Boolean ?= false,
    @Embedded
    val rocket: Rocket,
    @Embedded
    val links: Links?) : Parcelable {

    @IgnoredOnParcel
    @Ignore
    private val launchDateTime = Instant.parse(launch_date_utc)

    val diffInDays : String
        get () = launchDateTime.until(Clock.System.now(), DateTimeUnit.DAY, TimeZone.UTC).toString()

    val upcoming : Boolean
        get () = (launchDateTime.until(Clock.System.now(), DateTimeUnit.DAY, TimeZone.UTC).toInt() < 0)

    val timeString : String
        get () = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(launch_date_utc))
}
