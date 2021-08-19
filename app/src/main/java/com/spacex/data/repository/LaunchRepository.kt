package com.spacex.data.repository

import com.spacex.data.local.launch.LaunchDao
import com.spacex.data.remote.SpaceXRemoteDataSource
import com.spacex.utils.performGetOperation
import javax.inject.Inject

class LaunchRepository @Inject constructor(
    private val remoteDataSource: SpaceXRemoteDataSource,
    private val localDataSource: LaunchDao) {

    fun getLaunches(
        year: List<Int>?,
        success: Int?,
        sort: Int?) = performGetOperation(

        databaseQuery = {
            if(year != null)
                localDataSource.getFilteredLaunchesYear(year, success, sort)
            else if(success != null || sort != null)
                localDataSource.getFilteredLaunches(success, sort)
            else
                localDataSource.getAllLaunches() },
        networkCall = { remoteDataSource.getRemoteLaunches() },
        saveCallResult = { localDataSource.insertAll(it) }
    )

    suspend fun getYears(): List<Int> = localDataSource.getYears()
}