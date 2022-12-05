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
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.Diet
import project.stn991602827stn991579365.chuantaiAndJunxiu.data.DietDao

/**
 * The class defines mostly the relevant database operations.
 */
class ManagedietFragmentViewModel(val dietDao: DietDao, application: Application) : ViewModel() {

    // Defines a Job.
    private var viewModelJob= Job()

    // Defines a UI Scope.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Get the LiveData from the database for observation.
    var total=dietDao.getTotal(10)
    var diets=dietDao.getAll()

    /**
     * Call the database delete() fun on uiScope.
     */
    fun delete(diet: Diet){
        uiScope.launch {
            deleteFromDatabase(diet)
        }
    }

    /**
     * Commit delete operation on database in the background.
     */
    suspend fun deleteFromDatabase(diet: Diet){
        withContext(Dispatchers.IO){
            dietDao.delete(diet)
        }
    }

}