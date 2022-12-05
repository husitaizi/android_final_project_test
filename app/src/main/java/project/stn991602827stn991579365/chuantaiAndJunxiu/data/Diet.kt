package project.stn991602827stn991579365.chuantaiAndJunxiu.data

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The data class is a model of Diet object in the database.
* */
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
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "time") val time: Time
) {
}