package project.stn991602827.chuantai.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DietDao {
    @Query("SELECT * FROM dieting ORDER BY  date DESC")
    fun getAll():LiveData<List<Diet>>

    @Query("SELECT * FROM dieting WHERE id IN (:dietIds)")
    fun loadAllByIds(dietIds:IntArray):List<Diet>

    @Insert
    fun insertAll(vararg diet: Diet)

    @Delete
    fun delete(diet: Diet)

    @Update
    fun updateJumping(diet: Diet)

    // get the last 10 diet's sum(calories)
    @Query("SELECT  SUM(calories) from dieting ORDER BY date DESC LIMIT :num")
    fun getTotal(num:Int): LiveData<Int>
}