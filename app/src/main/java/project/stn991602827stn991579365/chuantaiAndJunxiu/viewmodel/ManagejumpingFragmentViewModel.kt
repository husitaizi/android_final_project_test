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
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Jumping
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.JumpingDao

/**
 * The class defines mostly the relevant database operations.
 */
class ManagejumpingFragmentViewModel(val jumpingDao: JumpingDao,application: Application) : ViewModel() {

    // Defines a Job.
    private var viewModelJob= Job()

    // Defines a UI Scope.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Get the LiveData from the database for observation.
    var total=jumpingDao.getTotal(10)
    var jumps=jumpingDao.getAll()

    /**
     * Call the database delete() fun on uiScope.
     */
    fun delete(jump:Jumping){
        uiScope.launch {
            deleteFromDatabase(jump)
        }
    }

    /**
     * Commit delete operation on database in the background.
     */
    suspend fun deleteFromDatabase(jump: Jumping){
        withContext(Dispatchers.IO){
            jumpingDao.delete(jump)
        }
    }

}