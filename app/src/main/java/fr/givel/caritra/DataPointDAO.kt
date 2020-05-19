package fr.givel.caritra

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object interface for our DataPoints database, used by Room
 */
@Dao
interface DataPointDAO {
    @Query("SELECT * from dataPoint_table ORDER BY id DESC")
    fun getDataPoints(): LiveData<List<DataPoint>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataPoint: DataPoint)

    @Query("DELETE FROM dataPoint_table")
    suspend fun deleteAll()
}