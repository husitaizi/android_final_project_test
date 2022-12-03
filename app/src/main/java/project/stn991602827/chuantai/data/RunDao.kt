package project.stn991602827.chuantai.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface RunDao {
    @Query("SELECT * FROM running ORDER BY date DESC")
    //fun getAll():List<Run>
   fun getAll(): LiveData<List<Run>>

    @Query("SELECT * FROM running WHERE id IN (:runIds)")
    fun loadAllByIds(runIds:IntArray):List<Run>

    @Insert
    fun insertAll(vararg runs:Run)

    @Delete
    fun delete(run:Run)

    @Update
    fun updateRun(run:Run)

    // get the last 10 runs's sum(calories)
    @Query("SELECT  SUM(calories) from running ORDER BY date DESC LIMIT :num")
    fun getTotal(num:Int):LiveData<Int>




}