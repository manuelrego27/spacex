package com.spacex.di

import android.content.Context
import com.spacex.data.local.companyinfo.CompanyInfoDao
import com.spacex.data.local.companyinfo.CompanyInfoDatabase
import com.spacex.data.local.launch.LaunchDao
import com.spacex.data.local.launch.LaunchDatabase
import com.spacex.data.remote.SpaceXRemoteDataSource
import com.spacex.data.remote.SpaceXService
import com.spacex.data.repository.CompanyInfoRepository
import com.spacex.data.repository.LaunchRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.spacexdata.com/v3/"

    @Singleton
    @Provides
    fun provideMoshi() : Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi) : Retrofit  = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    @Provides
    fun provideSpaceXService(retrofit: Retrofit): SpaceXService = retrofit.create(SpaceXService::class.java)

    @Singleton
    @Provides
    fun provideSpaceXRemoteDataSource(characterService: SpaceXService) = SpaceXRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideLaunchDatabase(@ApplicationContext appContext: Context) = LaunchDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideCompanyInfoDatabase(@ApplicationContext appContext: Context) = CompanyInfoDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideLaunchDao(db: LaunchDatabase) = db.launchDao()

    @Singleton
    @Provides
    fun provideCompanyInfoDao(db: CompanyInfoDatabase) = db.companyInfoDao()

    @Singleton
    @Provides
    fun provideLaunchRepository(remoteDataSource: SpaceXRemoteDataSource, localDataSource: LaunchDao) = LaunchRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideCompanyInfoRepository(remoteDataSource: SpaceXRemoteDataSource, localDataSource: CompanyInfoDao) = CompanyInfoRepository(remoteDataSource, localDataSource)

}