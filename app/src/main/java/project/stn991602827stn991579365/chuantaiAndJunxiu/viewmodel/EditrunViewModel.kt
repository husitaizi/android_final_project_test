package project.stn991602827stn991579365.chuantaiAndJunxiu.viewmodel

/*
* Author: Chuantai Hu stn991602827, Junxiu Ma stn991579365
* 2022-12-4
* Version 2.0
* Description: Final project
* The class defines mostly the relevant database operations.
*/
import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Run
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.RunDao

/**
 * The class defines mostly the relevant database operations.
 */
class EditrunViewModel(
    private val runDao: RunDao,
    private val application: Application) : ViewModel() {

    // Defines a Job.
    private var viewModelJob = Job()

    // Defines a UI Scope.
    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    /**
     * Call the database insert() fun on uiScope.
     */
    fun addARun(run: Run) {
        uiScope.launch {
            insert(run)
        }
    }

    /**
     * Commit insert operation on database in the background.
     */
    suspend fun insert(run: Run) {
        withContext(Dispatchers.IO) {
            runDao.insertAll(run)
        }
    }

    /**
     * Call the database update fun on uiScope.
     */
    fun updateARun(run:Run){
        uiScope.launch {
            update(run)
        }
    }

    /**
     * Commit update operation on database in the background.
     */
    suspend fun update(run:Run){
        withContext(Dispatchers.IO){
            runDao.updateRun(run)
        }
    }
}