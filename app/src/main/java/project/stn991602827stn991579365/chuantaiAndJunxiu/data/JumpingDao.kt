package project.stn991602827stn991579365.chuantaiAndJunxiu.data

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The interface is a data access object for the Jumping.
* */
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JumpingDao {
        /**
         * It's set in LiveData type to be observed in ManagejumpFragment to refresh the UI info.
         */
        @Query("SELECT * FROM rope_jumping ORDER BY date DESC")
        fun getAll():LiveData<List<Jumping>>

        @Query("SELECT * FROM rope_jumping WHERE id IN (:jumpingIds)")
        fun loadAllByIds(jumpingIds:IntArray):LiveData<List<Jumping>>

        @Insert
        fun insertAll(vararg jumpings:Jumping)

        @Delete
        fun delete(jumping: Jumping)

        @Update
        fun updateJumping(jumping: Jumping)

        /**
         * Sum the latest "num" of Diet calories.
         */
        @Query("SELECT  SUM(calories) from rope_jumping ORDER BY date DESC LIMIT :num")
        fun getTotal(num:Int):LiveData<Int>
    }
