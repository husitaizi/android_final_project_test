package project.stn991602827.chuantai.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DietDao {
    @Query("SELECT * FROM dieting")
    fun getAll():List<Diet>

    @Query("SELECT * FROM dieting WHERE id IN (:dietIds)")
    fun loadAllByIds(dietIds:IntArray):List<Diet>

    @Insert
    fun insertAll(vararg diet: Diet)

    @Delete
    fun delete(diet: Diet)
}