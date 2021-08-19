package com.spacex.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "company_info")
@Parcelize
data class CompanyInfo(
    @PrimaryKey
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val launch_sites: Int,
    val valuation: Long) : Parcelable {

    val summaryString
        get() = "$name was founded by $founder in $founded. It has now $employees employees, $launch_sites launch sites, and is valued at USD $valuation."
}
