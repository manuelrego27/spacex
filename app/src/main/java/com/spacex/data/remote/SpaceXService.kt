package com.spacex.data.remote

import com.spacex.data.entities.CompanyInfo
import com.spacex.data.entities.Launch
import retrofit2.Response
import retrofit2.http.GET

interface SpaceXService {
    @GET("launches")
    suspend fun getAllLaunches(): Response<List<Launch>>

    @GET("info")
    suspend fun getCompanyInfo(): Response<CompanyInfo>
}