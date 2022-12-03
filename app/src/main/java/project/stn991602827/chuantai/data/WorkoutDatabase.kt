package project.stn991602827.chuantai.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Run::class,Jumping::class,Diet::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WorkoutDatabase:RoomDatabase() {
    abstract fun runDao():RunDao
    abstract fun jumpingDao():JumpingDao
    abstract fun dietDao():DietDao

    companion object{
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
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance
            }
        }

    }
}