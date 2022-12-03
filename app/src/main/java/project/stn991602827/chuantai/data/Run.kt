package project.stn991602827.chuantai.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time


@Entity(tableName = "running")
data class  Run(
        @PrimaryKey(autoGenerate = true) val id:Int,
        @ColumnInfo(name="date") val date: Date,
        @ColumnInfo(name = "time") val time: Time,
        @ColumnInfo(name="calories") val distance:Int
        ){
}