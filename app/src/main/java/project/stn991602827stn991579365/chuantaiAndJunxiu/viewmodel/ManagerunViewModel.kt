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
class ManagerunViewModel(val runDao: RunDao, application: Application) : ViewModel() {

    // Get the LiveData from the database for observation.
    val runs = runDao.getAll()
    var total = runDao.getTotal(10)

    // Defines a Job.
    private var viewModelJob = Job()

    // Defines a UI Scope.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Call the database delete() fun on uiScope.
     */
    fun delete(run: Run) {
        uiScope.launch {
            deleteFromDatabase(run)
        }
    }

    /**
     * Commit delete operation on database in the background.
     */
    suspend fun deleteFromDatabase(run: Run) {
        withContext(Dispatchers.IO) {
            runDao.delete(run)
        }
    }
}

