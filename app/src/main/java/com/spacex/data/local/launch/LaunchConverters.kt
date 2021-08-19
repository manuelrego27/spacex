package com.spacex.data.local.launch

import androidx.room.TypeConverter
import com.spacex.data.entities.Links
import com.spacex.data.entities.Rocket

class LaunchConverters {
    @TypeConverter
    fun rocketFromString(
        string: String): Rocket {
        val tempArray: List<String> = string.split(' ')
        return Rocket(tempArray[0], tempArray[1])
    }

    @TypeConverter
    fun stringFromRocket(
        rocket: Rocket): String {
        return rocket.rocket_name+' '+rocket.rocket_type;
    }

    @TypeConverter
    fun linksFromString(
        string: String): Links {
        val tempArray: List<String> = string.split(' ')
        return Links(tempArray[0], tempArray[1], tempArray[2], tempArray[3])
    }

    @TypeConverter
    fun stringFromLink(
        links: Links): String {
        return links.mission_patch + ' ' + links.article_link + ' ' + links.wikipedia + ' ' + links.video_link
    }

}