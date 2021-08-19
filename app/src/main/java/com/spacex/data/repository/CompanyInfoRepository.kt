package com.spacex.data.repository

import com.spacex.data.local.companyinfo.CompanyInfoDao
import com.spacex.data.remote.SpaceXRemoteDataSource
import com.spacex.utils.performGetOperation
import javax.inject.Inject

class CompanyInfoRepository @Inject constructor(
    private val remoteDataSource: SpaceXRemoteDataSource,
    private val localDataSource: CompanyInfoDao) {

    fun getInfo() = performGetOperation(
        databaseQuery = { localDataSource.getCompanyInfo() },
        networkCall = { remoteDataSource.getCompanyInfo() },
        saveCallResult = { localDataSource.insert(it) }
    )
}