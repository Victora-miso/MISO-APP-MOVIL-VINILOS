package com.alphazetakapp.misoappvinilos.data.repository

import com.alphazetakapp.misoappvinilos.data.local.dao.CollectorDao
import com.alphazetakapp.misoappvinilos.data.model.Collector
import com.alphazetakapp.misoappvinilos.data.remote.dto.toCollector
import com.alphazetakapp.misoappvinilos.data.remote.service.CollectorService
import javax.inject.Inject

class CollectorRepository @Inject constructor(
    private val collectorDao: CollectorDao,
    private val collectorService: CollectorService
){
    suspend fun getCollectors(): Result<List<Collector>> {
        return try {
            val response = collectorService.getCollectors()
            if (response.isSuccessful) {
                response.body()?.let { collectorsDTO ->
                    // Convert the DTO to Collector entities
                    val collectors = collectorsDTO.map { it.toCollector() }
                    // Save in cache
                    collectorDao.insertAll(collectors)
                    Result.success(collectors)
            }?: Result.failure(Exception("Data is null"))
        }
            else {
                // If the API fails, we try recovery from cache
                Result.success(collectorDao.getCollectors())
            }
        } catch (e: Exception) {
            Result.failure(e)
            }
    }

    suspend fun getCollectorById(id: Int): Result<Collector> {
        return try {
            val response = collectorService.getCollectorsById(id)
            if (response.isSuccessful) {
                response.body()?.let { collectorDTO ->
                    Result.success(collectorDTO.toCollector())
                } ?: Result.failure(Exception("Collector not found"))
        }else{
            val cachedCollector = collectorDao.getCollectorsById(id)
            cachedCollector?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Collector not found in cache"))
        }
            } catch (e: Exception) {
                Result.failure(e)
        }
    }
}