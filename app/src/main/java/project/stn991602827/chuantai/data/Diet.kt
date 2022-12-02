package project.stn991602827.chuantai.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

@Entity(tableName = "dieting")
data class Diet(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "food") val food:String,
    @ColumnInfo(name = "calories") val calories:Int,
    @ColumnInfo(name = "data") val date: Date,
 //   @ColumnInfo(name = "time") val time: Time
) {
}