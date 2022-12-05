package project.stn991602827stn991579365.chuantaiAndJunxiu.data

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The interface is a data access object for the Run.
*/
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface RunDao {
    /**
     * It's set in LiveData type to be observed in ManagerunFragment to refresh the UI info.
     */
    @Query("SELECT * FROM running ORDER BY date DESC")
    fun getAll(): LiveData<List<Run>>

    @Query("SELECT * FROM running WHERE id IN (:runIds)")
    fun loadAllByIds(runIds: IntArray): List<Run>

    @Insert
    fun insertAll(vararg runs: Run)

    @Delete
    fun delete(run: Run)

    @Update
    fun updateRun(run: Run)

    /**
     * Sum the latest "num" of Run calories.
     */
    @Query("SELECT  SUM(calories) from running ORDER BY date DESC LIMIT :num")
    fun getTotal(num: Int): LiveData<Int>


}