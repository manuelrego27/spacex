package com.spacex.data.local.launch

import androidx.lifecycle.LiveData
import androidx.room.*
import com.spacex.data.entities.Launch

@Dao
interface LaunchDao {

    object LaunchFilter {
        const val FILTER_YEAR = "year"
        const val FILTER_SUCCESS = "success"
        const val FILTER_SORT = "sort"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(night: Launch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(launches: List<Launch>)

    @Update
    suspend fun update(night: Launch)

    @Query("DELETE FROM launch_table")
    suspend fun clear()

    @Query("SELECT * FROM launch_table")
    fun getAllLaunches(): LiveData<List<Launch>>

    @Query("SELECT DISTINCT launch_year FROM launch_table")
    suspend fun getYears(): List<Int>

    @Query("""SELECT * FROM launch_table WHERE
        (launch_year IN (:year)) AND
        (:success IS NULL OR launch_success LIKE :success)
        ORDER BY CASE WHEN :sort = 1 THEN flight_number END ASC, CASE WHEN :sort = 0 THEN flight_number END DESC""")
    fun getFilteredLaunchesYear(year: List<Int>?, success :Int?, sort: Int?): LiveData<List<Launch>>

    @Query("""SELECT * FROM launch_table WHERE
        (:success IS NULL OR launch_success LIKE :success)
        ORDER BY CASE WHEN :sort = 1 THEN flight_number END ASC, CASE WHEN :sort = 0 THEN flight_number END DESC""")
    fun getFilteredLaunches(success :Int?, sort: Int?): LiveData<List<Launch>>

}

