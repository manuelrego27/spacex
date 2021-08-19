package com.spacex.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    val mission_patch: String ?= null,
    val article_link: String ?= null,
    val wikipedia: String ?= null,
    val video_link: String ?= null) : Parcelable {}
