package com.alphazetakapp.misoappvinilos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alphazetakapp.misoappvinilos.data.model.Collector

@Dao
interface CollectorDao {
    @Query("SELECT * FROM collector")
    suspend fun getCollectors(): List<Collector>

    @Query("SELECT * FROM collector WHERE id = :collectorId")
    suspend fun getCollectorsById(collectorId: Int): Collector?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collector: List<Collector>)
}
