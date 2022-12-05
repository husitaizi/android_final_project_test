package project.stn991602827stn991579365.chuantaiAndJunxiu.data


/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class is for convert data types as needed.
* */
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

    /**
     *  Convert from string to sql date. like from "2015-03-31" to 2015-03-31
     */
    @TypeConverter
    fun stringToDate(str: String): Date {
        return Date.valueOf(str)
    }

    /**
     *  Convert from sql date to string.
     */
    @TypeConverter
    fun dateToString(date: Date): String {
        return date.toString()
    }

    /**
     * Convert from string to sql time.
     */
    @TypeConverter
    fun stringToTime(str: String): Time {
        return Time.valueOf(str)
    }

    /**
     * Convert from sql time to string.
     */
    @TypeConverter
    fun timeToString(time: Time): String {
        return time.toString()
    }

    /**
     * Retrieve the day info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getDayFromArgs(strArgs: String): Int {
        return strArgs.take(8).takeLast(2).toInt()
    }

    /**
     * Retrieve the Month info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getMonthFromArgs(strArgs: String): Int {
        return (strArgs.take(6).takeLast(2).toInt()-1)%12
    }

    /**
     * Retrieve the Year info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getYearFromArgs(strArgs: String): Int {
        return strArgs.take(4).toInt()
    }

    /**
     * Retrieve the Hour info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getHourFromArgs(strArgs: String): Int {
        return strArgs.take(10).takeLast(2).toInt()
    }

    /**
     * Retrieve the Minute info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getMinuteFromArgs(strArgs: String): Int {
        return strArgs.take(12).takeLast(2).toInt()
    }

    /**
     * Retrieve the Distance info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getDistanceFromArgs(strArgs: String): Int {
        return strArgs.let {
            var length=it.length
            it.takeLast((length-14)).toInt() }
    }

    /**
     * Retrieve the Count of Rope-jumping info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getCountFromArgs(strArgs: String): Int {
        return strArgs.let {
            var length=it.length
            it.takeLast((length-14)).toInt() }
    }

    /**
     * Retrieve the Calories info from navigation passed arguments.
     * The arguments is in the pattern of  "yyyymmddhhmmnnnn*" n is distance/count/calories amount.
     */
    fun getCaloriesFromArgs(strArgs: String):Int{
        return strArgs.let {
            var length=it.length
            it.takeLast((length-14)).toInt()
        }
    }

}