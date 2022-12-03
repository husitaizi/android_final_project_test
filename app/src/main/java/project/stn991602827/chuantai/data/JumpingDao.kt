package project.stn991602827.chuantai.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JumpingDao {

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

        // get the last 10 jumping's sum(calories)
        @Query("SELECT  SUM(calories) from rope_jumping ORDER BY date DESC LIMIT :num")
        fun getTotal(num:Int):LiveData<Int>
    }
