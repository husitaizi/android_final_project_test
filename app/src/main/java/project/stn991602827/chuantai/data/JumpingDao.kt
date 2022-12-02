package project.stn991602827.chuantai.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JumpingDao {

        @Query("SELECT * FROM rope_jumping")
        fun getAll():List<Jumping>

        @Query("SELECT * FROM rope_jumping WHERE id IN (:jumpingIds)")
        fun loadAllByIds(jumpingIds:IntArray):List<Jumping>

        @Insert
        fun insertAll(vararg jumpings:Jumping)

        @Delete
        fun delete(jumping: Jumping)
    }
