package com.spacex.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rocket(
    val rocket_name: String,
    val rocket_type: String) : Parcelable {}
