package com.spacex.data.local.companyinfo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.spacex.data.entities.CompanyInfo

@Dao
interface CompanyInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(companyInfo: CompanyInfo)

    @Update
    suspend fun update(companyInfo: CompanyInfo)

    @Query("DELETE FROM company_info")
    suspend fun clear()

    @Query("SELECT * FROM company_info")
    fun getCompanyInfo(): LiveData<CompanyInfo>
}

