package project.stn991602827stn991579365.chuantaiAndJunxiu.data

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The Room database. Contains tables: running, jump and diet.
*/
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The database abstract data class to be used to generate the Room database.
 * TypeConverters specified for the intaken date time info to be converted to sql date and sql time
 * type to be inserted into the database.
 * The current version number is 4 because the changing of schemas, which caused migrations.
 */
@Database(entities = [Run::class,Jumping::class,Diet::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WorkoutDatabase:RoomDatabase() {

    /**
     * The Room will generate the Daos automatically.
     */
    abstract fun runDao():RunDao
    abstract fun jumpingDao():JumpingDao
    abstract fun dietDao():DietDao

    /**
     * Define and generate the Room database.
     */
    companion object{
        // Annotation @volatile is to make the instance unique.
        @Volatile
        private var INSTANCE:WorkoutDatabase?=null

        fun getInstance(context: Context):WorkoutDatabase{
            synchronized(this){
            var instance = INSTANCE

            if(instance==null){
                instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        WorkoutDatabase::class.java,"workout")
                        // To enforce the database version matches the schema version.
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance
            }
        }
    }
}