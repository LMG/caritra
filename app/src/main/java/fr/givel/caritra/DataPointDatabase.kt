package fr.givel.caritra

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The Room database containing our DataPoints table (entity)
 * It should be abstract and it will be implemented by Room
 */
@Database(entities = [DataPoint::class], version = 1, exportSchema = true)
public abstract class DataPointDatabase : RoomDatabase() {
    abstract fun dataPointDao(): DataPointDAO

    companion object {
        // Singleton, so that only one database opens at once
        @Volatile
        private var INSTANCE: DataPointDatabase? = null

        /**
         * Returns the database, creates it if needed.
         * @param context: Context the application context for the database
         * @param scope: CoroutineScope the scope in which to run the database population
         *  coroutine on first run, since we can't run it in the UI thread.
         */
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): DataPointDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataPointDatabase::class.java,
                        "dataPoints_database"
                    )
                    .addCallback(DataPointDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class DataPointDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) // onOpen for testing. TODO: OnCreate
        {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dataPointDao())
                }
            }
        }

        suspend fun populateDatabase(dataPointDAO: DataPointDAO)
        {
            // Delete all content
            dataPointDAO.deleteAll()

            // Add sample data points
            var dataPoint = DataPoint(1)
            dataPointDAO.insert(dataPoint)
            dataPoint = DataPoint(5)
            dataPointDAO.insert(dataPoint)
            dataPoint = DataPoint(3)
            dataPointDAO.insert(dataPoint)
        }
    }
}