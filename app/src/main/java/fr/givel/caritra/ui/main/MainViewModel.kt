package fr.givel.caritra.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fr.givel.caritra.DataPoint
import fr.givel.caritra.DataPointDatabase
import fr.givel.caritra.DataPointRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The ViewModel has a lifecycle aligned to the Application.
 * It survives the death of Activities and Fragments.
 * We manage the database in this context so as to survive configuration changes (rotation...)
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    // The actual database (in its repository)
    private val repository: DataPointRepository
    // Provide access to all the data points
    // LiveData so we don't have to poll, and update when needed
    val allDataPoints: LiveData<List<DataPoint>>

    init {
        // We access everything through the repo, that needs the DAO, that we get from the database
        val dataPointsDao = DataPointDatabase.getDatabase(application, viewModelScope).dataPointDao()
        repository = DataPointRepository(dataPointsDao)
        allDataPoints = repository.allDataPoints
    }

    /**
     * Inserts DataPoints in the dataPoint table in a non-blocking way, using a coroutine in the
     * ViewModel scope (application and not activity related)
     */
    fun insert(dataPoint: DataPoint) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(dataPoint)
    }
}
