package com.spacex.data.remote

import javax.inject.Inject

class SpaceXRemoteDataSource @Inject constructor(
    private val launchService: SpaceXService) : BaseDataSource() {

    suspend fun getRemoteLaunches() = getResult { launchService.getAllLaunches() }
    suspend fun getCompanyInfo() = getResult { launchService.getCompanyInfo() }
}