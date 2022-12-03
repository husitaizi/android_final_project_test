package project.stn991602827.chuantai.data

import androidx.room.TypeConverter
import java.sql.Date
import java.sql.Time

class Converters {
/*    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }*/

    // "2015-03-31" to 2015-03-31
    @TypeConverter
    fun stringToDate(str: String): Date {
        return Date.valueOf(str)
    }

    @TypeConverter
    fun dateToString(date: Date): String {
        return date.toString()
    }

    @TypeConverter
    fun stringToTime(str: String): Time {
        return Time.valueOf(str)
    }

    @TypeConverter
    fun timeToString(time: Time): String {
        return time.toString()
    }

    fun getDayFromArgs(strArgs: String): Int {
        return strArgs.take(8).takeLast(2).toInt()
    }

    fun getMonthFromArgs(strArgs: String): Int {
        return (strArgs.take(6).takeLast(2).toInt()-1)%12
    }

    fun getYearFromArgs(strArgs: String): Int {
        return strArgs.take(4).toInt()
    }

    fun getHourFromArgs(strArgs: String): Int {
        return strArgs.take(10).takeLast(2).toInt()
    }

    fun getMinuteFromArgs(strArgs: String): Int {
        return strArgs.take(12).takeLast(2).toInt()
    }
    fun getDistanceFromArgs(strArgs: String): Int {
        return strArgs.let {
            var length=it.length
            it.takeLast((length-14)).toInt() }
    }
    fun getCountFromArgs(strArgs: String): Int {
        return strArgs.let {
            var length=it.length
            it.takeLast((length-14)).toInt() }
    }

    fun getCaloriesFromArgs(strArgs: String):Int{
        return strArgs.let {
            var length=it.length
            it.takeLast((length-14)).toInt()
        }
    }

}