package fr.givel.caritra

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SQLite table containing the data points entered by the user
 * For now a data point is a value
 */
@Entity(tableName = "dataPoint_table")
data class DataPoint(
    @ColumnInfo(name = "value") var value: Int)
{
    @PrimaryKey(autoGenerate = true) var id: Int = 0 //not in constructor because auto generated, value preset, auto name
}