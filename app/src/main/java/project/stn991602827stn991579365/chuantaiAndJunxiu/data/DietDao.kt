package project.stn991602827stn991579365.chuantaiAndJunxiu.data

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The interface is a data access object for the Diet.
* */
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DietDao {
    /**
     * It's set in LiveData type to be observed in ManagedietFragment to refresh the UI info.
     */
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

    /**
     * Sum the latest "num" of Diet calories.
     */
    @Query("SELECT  SUM(calories) from dieting ORDER BY date DESC LIMIT :num")
    fun getTotal(num:Int): LiveData<Int>
}