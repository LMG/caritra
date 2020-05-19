package fr.givel.caritra

import androidx.lifecycle.LiveData

/**
 * This class acts as an interface between our application and the database
 * if needed, it can be extended to fetch from a network
 * For now, it interacts with the database only, through the DAO
 */
class DataPointRepository (private val dataPointDAO: DataPointDAO)
{
    // This LiveData will be updated by room in a separate thread
    // if we observe it, we will be notified when it changes
    val allDataPoints: LiveData<List<DataPoint>> = dataPointDAO.getDataPoints()

    suspend fun insert(dataPoint: DataPoint) {
        dataPointDAO.insert(dataPoint)
    }
}